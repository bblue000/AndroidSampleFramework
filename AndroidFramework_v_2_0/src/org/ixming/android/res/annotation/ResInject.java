/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ixming.android.res.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ixming.android.res.ResTargetType;

/**
 * 用来设置Activity或者View中声明的一些资源对象；
 * 
 * 以便框架运行时注入，不必重复编写获取，赋值代码。
 * 
 * @author Yin Yong
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResInject {
	/**
	 * res/ 文件夹中的ID
	 * @return
	 */
    int id();

    /**
     * 制定资源类型
     * @see {@link ResTargetType}
     */
    ResTargetType type();
}
