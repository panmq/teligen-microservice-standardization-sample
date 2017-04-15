package com.teligen.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.teligen.domain.ResourceHandle;
import com.teligen.repository.ResourceHandleRepository;

/**
 * 作用： ① 测试服务实例的相关内容 ② 为后来的服务做提供
 */
@Profile("dispach")
@RestController
public class ResouceHandleController {
	
	private static final Logger LOGGER = Logger.getLogger(ResouceHandleController.class);

	// @Value("${spring.application.name}")
	// private String handlerName;

	@Autowired
	private ResourceHandleRepository resourceHandleRepository;

	/**
	 * 注：@GetMapping("/{id}")是spring 4.3的新注解等价于：
	 * 
	 * @RequestMapping(value = "/id", method = RequestMethod.GET)
	 *                       类似的注解还有@PostMapping等等
	 * @param id
	 * @return ResourceHandle信息
	 */
	@GetMapping("/resourceHanles/{id}")
	public ResourceHandle findById(@PathVariable Long id) {
		ResourceHandle findOne = this.resourceHandleRepository.findOne(id);
		return findOne;
	}

	@GetMapping("/standardiztion/processPermission/{resourceType}/{fileName}/{handlerName}")
	public String getProcessPermission(@Context HttpServletResponse response, @PathVariable String resourceType,
			@PathVariable String fileName, @PathVariable String handlerName) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JsonObject obj = new JsonObject();
		List<ResourceHandle> rhs = this.resourceHandleRepository
				.findByResourceTypeAndFileName(Integer.valueOf(resourceType), fileName);
		// TODO: 设置其他系统条件
		if (Runtime.getRuntime().freeMemory() < 1024 * 1024 * 1024) {
			obj.addProperty("status", "2");
			obj.addProperty("memo", "存储服务器上忙（内存小于1G），本次不处理");
		} else if (rhs.isEmpty()) {
			ResourceHandle rh = new ResourceHandle();
			Date date = new Date();
			rh.setResourceType(resourceType);
			// path
			rh.setFileName(fileName);
			// suffix
			// ..
			rh.setDiscoverTime(date);
			// ..
			rh.setHandlerName(handlerName);
			rh.setDiscoverTime(date);
			// ..
			this.resourceHandleRepository.save(rh);
			obj.addProperty("status", "0");
			obj.addProperty("memo", "允许处理");
		} else if (rhs.size() == 1 && rhs.get(0).getHandleTime() != null) {
			obj.addProperty("status", "1");
			obj.addProperty("memo", rhs.get(0).getHandlerName() + "正在处理");
		} else {
			obj.addProperty("status", "2");
			obj.addProperty("memo", "未明异常");
		}
		return obj.toString();
	}

	@GetMapping("/standardiztion/completeProcess/{resourceType}/{fileName}")
	public String completeProcess(@Context HttpServletResponse response, @PathVariable String resourceType,
			@PathVariable String fileName, @PathVariable String handlerName) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JsonObject obj = new JsonObject();
		int result = this.resourceHandleRepository.update(new Date(), Integer.valueOf(resourceType), fileName);
		obj.addProperty("status", result);
		obj.addProperty("memo", "");
		return obj.toString();
	}

}
