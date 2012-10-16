/*
 * Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.rave.portal.repository;

import org.apache.rave.portal.model.Page;
import org.apache.rave.portal.model.User;
import org.apache.rave.portal.model.Widget;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 */
public interface MongoModelOperations<T> {
    long count(Query query);
    void remove(Query query);
    T findOne(Query query);
    List<T> find(Query query);
    T get(long id);
    T save(T item);

    public static interface MongoPageOperations extends MongoModelOperations<Page> {}
    public static interface MongoUserOperations extends MongoModelOperations<User> {}
    public static interface MongoWidgetOperations extends MongoModelOperations<Widget> {}
}