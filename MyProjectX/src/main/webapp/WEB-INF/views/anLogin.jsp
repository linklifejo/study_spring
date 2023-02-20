<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.hanul.app.dto.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>  

<%		
	Gson gson = new Gson();
	String json = gson.toJson((MemberDTO)request.getAttribute("anLogin"));
	
	out.println(json);	
	
%>
