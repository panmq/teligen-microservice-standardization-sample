package com.teligen.user.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import com.teligen.user.entity.User;
import com.teligen.user.feign.UserFeignHystrixClient;

/**
 * 关于同步REST调用有两种选择，
 * 从功能上说，FeignClient的比RestTemplate多了熔断的功能
 * 从实现上说，FeignClient需要定义接口和fallback类，而RestTemplate需要自我添加@LoadBalanced注释
 *
 * 关于异步REST调用目前还有缺陷，现在调用的URL里不能用service name而用了host:port (详情看本类里的TODO)
 */
@RestController
public class FeignHystrixController {
	@Autowired
	private UserFeignHystrixClient userFeignHystrixClient;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AsyncRestTemplate asyncRestTemplate;

	@Value("${async.timeoutInSec}")
	private String asyncTimeoutInSec;

	@GetMapping("feign/{id}")
	public User findByIdFeign(@PathVariable Long id) {
		User user = this.userFeignHystrixClient.findByIdFeign(id);
		return user;
	}

	@GetMapping("sync/{id}")
	public User findByIdSync(@PathVariable Long id) {
		// 应该跳到gateway的url，不要直接跳
		User user = this.restTemplate.getForEntity("http://teligen-microservice-provider-sample/" + id, User.class).getBody();
		return user;
	}

	/**
	 * future.get是会阻塞的，不想这样的话，继续用Callable返回吧
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("async/{id}")
	public User findByIdAsync(@PathVariable Long id) {
		// 应该跳到gateway的url，不要直接跳
		ListenableFuture<ResponseEntity<User>> future = this.asyncRestTemplate
				// TODO: 在这里，@LoadBalanced注释不能处理URL的service name了，导致要写死host:port
				// .getForEntity("http://teligen-microservice-provider-sample/callback/" + id, User.class);
				.getForEntity("http://discovery:8000/callback/" + id, User.class);
		// 设置异步回调
		future.addCallback(new ListenableFutureCallback<ResponseEntity<User>>() {
			@Override
			public void onSuccess(ResponseEntity<User> result) {
				System.out.println("======client get result : " + result.getBody());
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("======client failure : " + t);
			}
		});
		try {
			ResponseEntity<User> resp = future.get(Integer.parseInt(this.asyncTimeoutInSec), TimeUnit.SECONDS);
			return resp.getBody();
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
