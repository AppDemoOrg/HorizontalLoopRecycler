package com.abt.basic.concurrent.builder;

import com.abt.basic.concurrent.ThreadType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @描述：     @创建缓存线程池
 * @作者：     @黄卫旗
 * @创建时间： @2017-04-25
 */
public class CachedBuilder extends AbstractBuilder<ExecutorService> {
	@Override
	protected ExecutorService create() {
		return Executors.newCachedThreadPool();
	}

	@Override
	protected ThreadType getType() {
		return ThreadType.CACHED;
	}

}
