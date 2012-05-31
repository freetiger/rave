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
package org.apache.rave.portal.model.conversion;

import static org.junit.Assert.*;

import org.apache.rave.portal.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:test-applicationContext.xml","classpath:test-dataContext.xml"} )
public class JpaGroupConverterTest {

//    private Group group;
//
//    private String description = "Test Group";
//    private Long entityId = Long.valueOf(400);
//    private String title = "GroupTitle";
//    private Person owner = null;
//    private List<Person> members = null;
//
//    private String ownerDisplayName = "Bob";
//    private String ownerGivenName = "Smith";
//
//    public void setup() {
//        members = new ArrayList<Person>();
//        owner = new PersonImpl();
//        owner.setDisplayName(ownerDisplayName);
//        owner.setGivenName(ownerGivenName);
//        members.add(owner);
//
//        group = new GroupImpl();
//        group.setDescription(description);
//        group.setEntityId(entityId);
//        group.setTitle(title);
//        group.setOwner(owner);
//        group.setMembers(members);
//    }
//
//    @Test
//    public void testConvertGroupToJpaGroup() {
//        JpaGroupConverter converter = new JpaGroupConverter();
//
//        JpaGroup jpaGroup = converter.convert(group);
//
//        assertEquals(description, jpaGroup.getDescription());
//        assertEquals(entityId, jpaGroup.getEntityId());
//        assertEquals(title, jpaGroup.getTitle());
//        assertEquals(owner, jpaGroup.getOwner());
//        assertEquals(members.size(), jpaGroup.getMembers().size());
//    }

}