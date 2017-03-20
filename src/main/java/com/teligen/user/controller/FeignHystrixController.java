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
//		User user = this.restTemplate.getForObject("http://teligen-microservice-provider-sample/" + id, User.class);
		// 应该跳到gateway的url，不要直接跳
		User user = this.restTemplate.getForObject("http://discovery:8000/" + id, User.class);
		return user;
	}

	/**
	 * future.get是会阻塞的，不想这样的话，继续用Callable返回吧
	 * @param id
	 * @return
	 */
	@GetMapping("async/{id}")
	public User findByIdAsync(@PathVariable Long id) {
		ListenableFuture<ResponseEntity<User>> future = this.asyncRestTemplate
//				.getForEntity("http://teligen-microservice-provider-sample/callback/" + id, User.class);
				// 应该跳到gateway的url，不要直接跳
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
		ResponseEntity resp;
		try {
			resp = future.get(Integer.parseInt(this.asyncTimeoutInSec), TimeUnit.SECONDS);
			return (User) resp.getBody();
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new RuntimeException();
	}

}
