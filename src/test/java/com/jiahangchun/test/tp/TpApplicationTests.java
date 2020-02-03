package com.jiahangchun.test.tp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiahangchun.test.tp.swagger.dto.OpenApi;
import com.jiahangchun.test.tp.swagger.dto.ReturnProperty;
import com.jiahangchun.test.tp.swagger.impl.ApiResponseServiceImpl;

class TpApplicationTests {

	public static void main(String[] args) {
		json();

		OpenApi openApi=new ApiResponseServiceImpl().getOriginSwaggerData(null);
		System.out.println("test parser.");

		String text=" {\"description\":\"图片名称\",\"type\":\"string\",\"example\":\"Xxx的门店图片\"}";
		JSONObject userJson = JSONObject.parseObject(text);
		ReturnProperty user = JSON.toJavaObject(userJson,ReturnProperty.class);
 		System.out.println("test parser2.");
	}

	public static void json(){
		String text="{\"schema\":{\"$ref\":\"#/definitions/更新shipId的参数\"}}";
		JSONObject userJson = JSONObject.parseObject(text);
//		SchemaDto map = JSON.toJavaObject(userJson, SchemaDto.class);
		System.out.println("....");
	}

}
