package com.mcoding.base.config;


import com.mcoding.common.util.ResponseResult;
import com.mcoding.common.exception.CommonException;
import com.mcoding.common.util.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ControllerAdvice
public class ControllerConfig {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseResult<String> exceptionHandler(HttpServletRequest request, HttpServletResponse response,
												   MethodArgumentNotValidException e) {
		List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
		String errorMessage = allErrors.stream().findFirst().map(ObjectError::getDefaultMessage).get();

		return ResponseResult.fail(ResponseCode.Fail, errorMessage);
	}

	@ExceptionHandler(value = CommonException.class)
	@ResponseBody
	public ResponseResult<String> exceptionHandler(HttpServletRequest request, HttpServletResponse response,
												   CommonException e) {
		return ResponseResult.fail(ResponseCode.Fail, e.getMessage());
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResponseResult<String> exceptionHandler(HttpServletRequest request, HttpServletResponse response,
												   Exception e) {
		e.printStackTrace();
		logger.error(e.getMessage());
		return ResponseResult.fail(ResponseCode.Fail, "接口调用异常,请联系管理员");
	}
}
