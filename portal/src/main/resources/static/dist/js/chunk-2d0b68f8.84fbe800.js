(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0b68f8"],{"1e4b":function(e,o,n){"use strict";n.r(o);var t=function(){var e=this,o=e.$createElement,n=e._self._c||o;return n("div",{staticClass:"content"},[n("div",{on:{click:e.sendSocketData}},[e._v("666")])])},c=[],s={data:function(){return{input:""}},created:function(){this.getSocketData()},methods:{getSocketData:function(){window.socket=void 0,"undefined"===typeof WebSocket?console.log("您的浏览器不支持WebSocket"):(window.socket=new WebSocket("ws://websocket.yaonie1995.xyz"),window.socket.onopen=function(){console.log("Socket 已打开")},window.socket.onmessage=function(e){console.log(e)},window.socket.onclose=function(){console.log("Socket 已关闭")},socket.onerror=function(){console.log("Socket 发生了错误,请刷新页面")})},sendSocketData:function(){var e=JSON.stringify({level:"",isPlaying:!1,avatar:"",topicGroupId:"",type:1,requestId:"",senderId:"",senderName:"",addresseeId:"",addresseeName:"",sendTime:0});window.socket&&window.socket.send(e)}}},i=s,d=n("2877"),a=Object(d["a"])(i,t,c,!1,null,"74996802",null);o["default"]=a.exports}}]);