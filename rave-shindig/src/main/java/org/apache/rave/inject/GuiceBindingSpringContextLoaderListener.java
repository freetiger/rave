/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.rave.inject;

import com.google.inject.Injector;
import com.google.inject.Module;
import org.apache.shindig.common.servlet.GuiceServletContextListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.util.Collection;

/**
 * Context Load listener that initializes the Spring application context and binds beans to the pre-existing Guice injector
 */
public class GuiceBindingSpringContextLoaderListener extends ContextLoaderListener{

    @Override
    public void contextInitialized(ServletContextEvent event) {
        //Initialize the application context
        super.contextInitialized(event);
        ServletContext servletContext = event.getServletContext();
        //Get a list of Guice module Spring beans from the application context
        Collection<Module> modules = getModulesFromApplicationContext();
        //Get the current injector from the servlet context
        Injector injector = getInjector(servletContext);
        //Override the current injector with the new child injector that includes all Spring managed Guice modules
        overrideInjector(injector.createChildInjector(modules), servletContext);
    }

    private static Injector getInjector(ServletContext servletContext) {
        Injector injector = (Injector) servletContext.getAttribute(GuiceServletContextListener.INJECTOR_ATTRIBUTE);
        if(injector == null) {
            throw new IllegalStateException("No Guice Injector found in Servlet Context.  Ensure that this listener is loaded AFTER Guice has been initialized");
        }
        return injector;
    }

    private static void overrideInjector(Injector injector, ServletContext context) {
        context.setAttribute(GuiceServletContextListener.INJECTOR_ATTRIBUTE, injector);
    }

    private static Collection<Module> getModulesFromApplicationContext() {
        ApplicationContext context = getCurrentWebApplicationContext();
        return context.getBeansOfType(Module.class).values();
    }
}
