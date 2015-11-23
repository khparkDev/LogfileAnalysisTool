package com.khpark.log.analysis.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.khpark.log.analysis.service.impl.LogFileInfoServiceImpl;

@Controller
@RequestMapping("/test")
public class TestMainController {

	/*
	@Autowired
	LogFileInfoServiceImpl testService;

	@RequestMapping("index")
	@ModelDescription(id = "title", description = "타이틀 제목", type = String.class)
	public ModelAndView index(TmonUser user, @ParamDescription("파라메터로 전달된 타이틀") @RequestParam(required = false, value = "title") String title) {

		return getModelAndView("template/index").addObject("title", title).addObject("value", Collections.emptyList());
	}
	*/

}
