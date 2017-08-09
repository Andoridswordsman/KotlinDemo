package com.astarh.kotlindemo.api;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by sll on 2016/3/31.
 */
public class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

  private Type type;
  private Charset charset;

  public FastJsonResponseBodyConverter() {
  }

  public FastJsonResponseBodyConverter(Type type, Charset charset) {
    this.type = type;
    this.charset = charset;
  }

  @Override
  public T convert(ResponseBody value) throws IOException {
    try {
//      ResultResponse resultResponse = JSON.parseObject(value.string(), ResultResponse.class);
//      if(resultResponse.getStatus().equals("10000")){
        return JSON.parseObject(value.string(), type);
//      }else if(resultResponse.getStatus().equals("10012")){
//        UIUtil.showToastAtCenter(XApplication.getContext(),"异地登录");
//        return null;
//      }
    } finally {
      value.close();
//      return null;
    }
  }
}

