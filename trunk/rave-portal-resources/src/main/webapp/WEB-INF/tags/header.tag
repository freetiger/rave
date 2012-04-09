<%--
  Licensed to the Apache Software Foundation (ASF) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The ASF licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
  --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="pageTitle" required="false" description="The title of the page" %>
<fmt:setBundle basename="messages"/>

<header>
    <nav class="topnav">
        <%--@elvariable id="topnav" type="org.apache.rave.portal.web.model.NavigationMenu"--%>
        <c:if test="${not empty topnav}">
            <ul class="horizontal-list">
                <c:forEach items="${topnav.navigationItems}" var="navItem">
                    <li><a href="<spring:url value="${navItem.url}"/>"><fmt:message key="${navItem.name}"/></a></li>
                </c:forEach>
            </ul>
        </c:if>
    </nav>
    <h1><c:out value="${pageTitle}"/></h1>
</header>
