(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-210d254a"],{"2c3a":function(e,s,t){"use strict";t.r(s);var i=function(){var e=this,s=e.$createElement,i=e._self._c||s;return i("div",{staticClass:"containter"},[i("div",{directives:[{name:"show",rawName:"v-show",value:e.userInfo,expression:"userInfo"}],staticClass:"content flex-col"},[i("div",{directives:[{name:"show",rawName:"v-show",value:e.mobileUtil.isPc,expression:"mobileUtil.isPc"}],staticClass:"modelFont"},[e._v("個人信息")]),i("div",{directives:[{name:"show",rawName:"v-show",value:e.mobileUtil.isPc,expression:"mobileUtil.isPc"}]},[i("span",{staticClass:"hexagon"},[e._v("Back to previous page")])]),i("div",{staticClass:"personalContent flex-col"},[i("div",{directives:[{name:"show",rawName:"v-show",value:e.mobileUtil.isPc,expression:"mobileUtil.isPc"}],staticClass:"personalContent-top flex-row flex-x-between"},[i("div",{staticClass:"personalContent-topLeft flex-row flex-y-center"},[i("img",{staticClass:"userImg",attrs:{src:t("3099")}}),i("span",{staticClass:"userName"},[e._v(e._s(e.userInfo.userName))]),i("span",[e._v(e._s(e.accountInfo.levelName))]),i("img",{staticClass:"diamondImg",attrs:{src:t("8e96")}}),i("span",{staticClass:"diamondNum"},[e._v(e._s(e.accountInfo.balance))])]),i("div",{staticClass:"personalContent-topRig flex-row flex-y-center"},[i("div",{staticClass:"titleFont"},[e._v("總得分")]),i("div",{staticClass:"numFont"},[e._v(e._s(e.accountInfo.levelScore))])])]),i("div",{directives:[{name:"show",rawName:"v-show",value:e.mobileUtil.isPhone,expression:"mobileUtil.isPhone"}],staticClass:"personalContent-top flex-col flex-x-center flex-y-center"},[i("img",{staticClass:"userImg",attrs:{src:t("3099")}}),i("div",{staticClass:"flex-row"},[i("span",{staticClass:"userName"},[e._v(e._s(e.userInfo.userName))]),i("span",{staticClass:"userName"},[e._v(e._s(e.accountInfo.levelName))])]),e._m(0)]),i("div",{staticClass:"personalContent-cen flex-x-between",class:e.mobileUtil.isPhone?"flex-col flex-y-center":"flex-row"},[i("div",{class:e.mobileUtil.isPhone?"flex-col flex-y-center":"flex-row"},[e._m(1),i("div",{staticClass:"personalContent-center flex-col"},[i("div",[e._v("中文名："+e._s(e.userInfo.chName))]),i("div",[e._v("英文名："+e._s(e.userInfo.enName))]),i("div",[e._v("學校："+e._s(e.userInfo.schoolName))]),i("div",[e._v("班級："+e._s(e.userInfo.classCode))]),i("div",[e._v("座號："+e._s(e.userInfo.seatNum))])])]),i("div",{staticClass:"personalContent-cenRig",on:{click:e.showEditUserDia}},[e._v("编辑")])]),i("div",{staticClass:"personalContent-bot flex-col"},[e._m(2),i("div",{staticClass:"personalContent-botRow",class:e.mobileUtil.isPhone?"flex-col":"flex-row flex-y-center"},[e._m(3),i("span",{staticClass:"tipFont"},[e._v("建議密碼由8位以上數字、字母和特殊字元組成。")]),i("div",{staticClass:"bindBut",on:{click:e.showModifyPasswdDia}},[e._v("修改密碼")])]),i("div",{staticClass:"personalContent-botRow",class:e.mobileUtil.isPhone?"flex-col":"flex-row flex-y-center"},[i("div",{staticClass:"flex-row flex-y-center"},[i("span",[e._v("手机绑定:")]),i("span",{staticClass:"bindMsg"},[e._v(e._s(e.userInfo.telephone?"已驗證":"未驗證"))])]),i("span",{staticClass:"tipFont"},[e._v(e._s(e.userInfo.telephone?"("+e.userInfo.telephone+")":"")+"綁定手機後，您即可享受手機登錄、手機找回密碼等服務")]),i("div",{staticClass:"bindBut",on:{click:function(s){e.checkPhoneDia=!0}}},[e._v(e._s(e.userInfo.telephone?"更换手機":"驗證手機"))])]),i("div",{staticClass:"personalContent-botRow",class:e.mobileUtil.isPhone?"flex-col":"flex-row flex-y-center"},[i("div",{staticClass:"flex-row flex-y-center"},[i("span",[e._v("邮箱绑定:")]),i("span",{staticClass:"bindMsg"},[e._v(e._s(e.userInfo.email?"已驗證":"未驗證"))])]),i("span",{staticClass:"tipFont"},[e._v(e._s(e.userInfo.email?"("+e.userInfo.email+")":"")+"綁定郵箱後，您即可享受郵箱登錄、郵箱找回密碼等服務")]),i("div",{staticClass:"bindBut",on:{click:function(s){e.checkEmailDia=!0}}},[e._v(e._s(e.userInfo.email?"更换郵箱":"驗證郵箱"))])])])])]),i("el-dialog",{staticClass:"diaSty SureRefuseDia",attrs:{title:" 編輯信息 ",visible:e.editUserDia,width:"30%"},on:{"update:visible":function(s){e.editUserDia=s}}},[i("el-form",{ref:"editUserForm",staticClass:"demo-ruleForm",staticStyle:{padding:"0 40px"},attrs:{model:e.editUserForm,rules:e.editUserRules,"label-width":"120px","label-position":"left"}},[i("el-form-item",{attrs:{prop:"chName"}},[i("span",{attrs:{slot:"label"},slot:"label"},[i("i",{staticClass:"el-icon-edit iconSty"}),e._v("中文名: ")]),i("el-input",{attrs:{placeholder:"请输入中文名"},model:{value:e.editUserForm.chName,callback:function(s){e.$set(e.editUserForm,"chName",s)},expression:"editUserForm.chName"}})],1),i("el-form-item",{attrs:{prop:"enName"}},[i("span",{attrs:{slot:"label"},slot:"label"},[i("i",{staticClass:"el-icon-edit iconSty"}),e._v("英文名: ")]),i("el-input",{attrs:{placeholder:"请输入英文名"},model:{value:e.editUserForm.enName,callback:function(s){e.$set(e.editUserForm,"enName",s)},expression:"editUserForm.enName"}})],1),i("el-form-item",{attrs:{prop:"seatNum"}},[i("span",{attrs:{slot:"label"},slot:"label"},[i("i",{staticClass:"el-icon-edit iconSty"}),e._v("座 號: ")]),i("el-input",{attrs:{placeholder:"请输入座號"},model:{value:e.editUserForm.seatNum,callback:function(s){e.$set(e.editUserForm,"seatNum",s)},expression:"editUserForm.seatNum"}})],1)],1),i("div",{staticClass:"dialog-footer flex-row flex-x-around",attrs:{slot:"footer"},slot:"footer"},[i("el-button",{on:{click:function(s){e.editUserDia=!1}}},[e._v("取 消")]),i("el-button",{on:{click:function(s){return e.submitEdit("editUserForm")}}},[e._v("确 定")])],1)],1),i("el-dialog",{staticClass:"diaSty SureRefuseDia",attrs:{title:" 修改密码 ",visible:e.modifyPasswdDia,width:"30%"},on:{"update:visible":function(s){e.modifyPasswdDia=s}}},[i("el-form",{ref:"modifyPasswdForm",staticClass:"demo-ruleForm",staticStyle:{padding:"0 40px"},attrs:{model:e.modifyPasswdForm,rules:e.modifyPasswdRules,"label-width":"70px"}},[i("el-form-item",{attrs:{label:"旧密码",prop:"oldPassword"}},[i("el-input",{attrs:{type:"password",placeholder:"请输入旧密码"},model:{value:e.modifyPasswdForm.oldPassword,callback:function(s){e.$set(e.modifyPasswdForm,"oldPassword",s)},expression:"modifyPasswdForm.oldPassword"}})],1),i("el-form-item",{attrs:{label:"新密码",prop:"newPassword"}},[i("el-input",{attrs:{type:"password",placeholder:"请输入新密码"},model:{value:e.modifyPasswdForm.newPassword,callback:function(s){e.$set(e.modifyPasswdForm,"newPassword",s)},expression:"modifyPasswdForm.newPassword"}})],1)],1),i("div",{staticClass:"dialog-footer flex-row flex-x-around",attrs:{slot:"footer"},slot:"footer"},[i("el-button",{on:{click:function(s){e.modifyPasswdDia=!1}}},[e._v("取 消")]),i("el-button",{on:{click:function(s){return e.submitModifyPasswd("modifyPasswdForm")}}},[e._v("确 定")])],1)],1),i("el-dialog",{staticClass:"SureRefuseDia",attrs:{title:" 验证手机 ",visible:e.checkPhoneDia,width:"30%"},on:{"update:visible":function(s){e.checkPhoneDia=s}}},[i("el-form",{ref:"bindForm",staticClass:"demo-ruleForm",attrs:{model:e.bindForm,rules:e.bindRules,"label-width":"100px"}},[i("el-form-item",{attrs:{prop:"contact"}},[i("span",{attrs:{slot:"label"},slot:"label"},[i("i",{staticClass:"el-icon-edit iconSty"}),e._v("手机号: ")]),i("el-input",{attrs:{placeholder:"请输入手机号"},model:{value:e.bindForm.contact,callback:function(s){e.$set(e.bindForm,"contact",s)},expression:"bindForm.contact"}})],1),i("el-form-item",{attrs:{prop:"code"}},[i("span",{attrs:{slot:"label"},slot:"label"},[i("i",{staticClass:"el-icon-edit iconSty"}),e._v("验证码: ")]),i("el-input",{staticStyle:{width:"60%"},attrs:{placeholder:"请输入验证码"},model:{value:e.bindForm.code,callback:function(s){e.$set(e.bindForm,"code",s)},expression:"bindForm.code"}}),i("el-button",{attrs:{type:"success",size:"mini",disabled:e.isDisabled},on:{click:function(s){return e.handleSendCode(0)}}},[e._v(e._s(e.codeText))])],1)],1),i("div",{staticClass:"dialog-footer flex-row flex-x-around",attrs:{slot:"footer"},slot:"footer"},[i("el-button",{on:{click:function(s){e.modifyPasswdDia=!1}}},[e._v("取 消")]),i("el-button",{on:{click:e.submitBind}},[e._v("确 定")])],1)],1),i("el-dialog",{staticClass:"SureRefuseDia",attrs:{title:" 验证邮箱 ",visible:e.checkEmailDia,width:"30%"},on:{"update:visible":function(s){e.checkEmailDia=s}}},[i("el-form",{ref:"bindForm",staticClass:"demo-ruleForm",attrs:{model:e.bindForm,rules:e.bindRules,"label-width":"100px"}},[i("el-form-item",{attrs:{prop:"contact"}},[i("span",{attrs:{slot:"label"},slot:"label"},[i("i",{staticClass:"el-icon-edit iconSty"}),e._v("邮箱号: ")]),i("el-input",{attrs:{placeholder:"请输入邮箱号"},model:{value:e.bindForm.contact,callback:function(s){e.$set(e.bindForm,"contact",s)},expression:"bindForm.contact"}})],1),i("el-form-item",{attrs:{prop:"code"}},[i("span",{attrs:{slot:"label"},slot:"label"},[i("i",{staticClass:"el-icon-edit iconSty"}),e._v("验证码: ")]),i("el-input",{staticStyle:{width:"60%"},attrs:{placeholder:"请输入验证码"},model:{value:e.bindForm.code,callback:function(s){e.$set(e.bindForm,"code",s)},expression:"bindForm.code"}}),i("el-button",{attrs:{type:"success",size:"mini",disabled:e.isDisabled},on:{click:function(s){return e.handleSendCode(1)}}},[e._v(e._s(e.codeText))])],1)],1),i("div",{staticClass:"dialog-footer flex-row flex-x-around",attrs:{slot:"footer"},slot:"footer"},[i("el-button",{on:{click:function(s){e.modifyPasswdDia=!1}}},[e._v("取 消")]),i("el-button",{on:{click:e.submitBind}},[e._v("确 定")])],1)],1)],1)},o=[function(){var e=this,s=e.$createElement,i=e._self._c||s;return i("div",{staticClass:"personalContent-topRight flex-row flex-y-center"},[i("img",{staticClass:"diamondImg",attrs:{src:t("8e96")}}),i("span",{staticClass:"diamondNum"},[e._v("4")])])},function(){var e=this,s=e.$createElement,i=e._self._c||s;return i("div",{staticClass:"personalContent-cenLeft flex-col flex-y-center"},[i("img",{attrs:{src:t("3099")}}),i("div",{staticClass:"editFont"},[i("i",{staticClass:"el-icon-edit"}),e._v("修改头像 ")])])},function(){var e=this,s=e.$createElement,t=e._self._c||s;return t("div",{staticClass:"personalContent-botRow flex-row flex-y-center"},[t("span",[e._v("安全等级:")]),t("p",{staticClass:"active"},[e._v("弱")]),t("p",[e._v("中")]),t("p",[e._v("强")])])},function(){var e=this,s=e.$createElement,t=e._self._c||s;return t("div",{staticClass:"flex-row flex-y-center"},[t("span",[e._v("密码强度:")]),t("p",{staticClass:"active"},[e._v("弱")]),t("p",[e._v("中")]),t("p",[e._v("强")])])}],a={data:function(){return{mobileUtil:{},userInfo:{},accountInfo:{},editUserDia:!1,editUserForm:{},editUserRules:{chName:[{required:!0,message:"请输入中文名",trigger:"blur"}],enName:[{required:!0,message:"请输入英文名",trigger:"blur"}],seatNum:[{required:!0,message:"请输入座號",trigger:"blur"}]},modifyPasswdDia:!1,modifyPasswdForm:{},checkPhoneForm:{},modifyPasswdRules:{oldPassword:[{required:!0,message:"请输入旧密码",trigger:"blur"}],newPassword:[{required:!0,message:"请输入新密码",trigger:"blur"}]},checkPhoneDia:!1,checkPhoneRules:{},bindForm:{},isDisabled:!1,checkEmailDia:!1,time:60,codeText:"获取验证码",bindRules:{contact:[{required:!0,trigger:"blur",message:"请输入手机号"}],code:[{required:!0,trigger:"blur",message:"请输入验证码"}]},myuserInfo:this.$store.state.userInfo}},created:function(){this.mobileUtil=JSON.parse(sessionStorage.getItem("mobileUtil")),this.init()},methods:{showModifyPasswdDia:function(){this.modifyPasswdDia=!0,this.modifyPasswdForm={}},showEditUserDia:function(){this.editUserDia=!0,this.editUserForm={chName:this.userInfo.chName,enName:this.userInfo.enName,seatNum:this.userInfo.seatNum}},init:function(){var e=this;this.$axios.getUserInfo({}).then((function(s){s.success&&(e.userInfo=s.data)})),this.$axios.getAccountInfo({userId:this.myuserInfo.userId}).then((function(s){s.success&&(e.accountInfo=s.data)}))},handleSendCode:function(e){var s=this;this.bindForm.type=e,this.$refs.bindForm.validateField("contact",(function(t){if(!t){s.isDisabled=!0;var i={type:e,contact:s.bindForm.contact};s.$axios.sendCaptcha(i).then((function(e){e.success?(s.$message({message:"验证码发送成功",type:"success"}),s.countdown()):s.isDisabled=!1}))}}))},countdown:function(){var e=this;this.t=setInterval((function(){e.time>0?(e.codeText=e.time+"s",e.time--):(clearInterval(e.t),e.codeText="获取验证码",e.time=60,e.isDisabled=!1)}),1e3)},submitEdit:function(e){var s=this;this.$refs[e].validate((function(e){if(!e)return!1;s.$axios.editUserInfo(s.editUserForm).then((function(e){e.success&&(s.editUserDia=!1,s.$message({message:"修改成功",type:"success"}),s.init())}))}))},submitModifyPasswd:function(e){var s=this;this.$refs[e].validate((function(e){if(!e)return!1;s.$axios.modifyPasswd(s.modifyPasswdForm).then((function(e){e.success&&(s.modifyPasswdDia=!1,s.$message({message:"修改成功",type:"success"}),s.init())}))}))},submitBind:function(){var e=this;this.$refs["bindForm"].validate((function(s){if(!s)return!1;e.$axios.verifyCaptcha(e.bindForm).then((function(s){s.success&&(e.checkPhoneDia=!1,e.checkEmailDia=!1,e.$message({message:"修改成功",type:"success"}),e.init())}))}))}}},l=a,n=(t("e6b0"),t("2877")),c=Object(n["a"])(l,i,o,!1,null,"6da1e9c0",null);s["default"]=c.exports},3099:function(e,s,t){e.exports=t.p+"img/competition1.659ab86f.png"},"8e96":function(e,s){e.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABwAAAAWCAYAAADTlvzyAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTM4IDc5LjE1OTgyNCwgMjAxNi8wOS8xNC0wMTowOTowMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTcgKFdpbmRvd3MpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjgwNTY3QTMxQjYyMTExRUFBNTFFRDU1M0U0REVFRjQxIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOjgwNTY3QTMyQjYyMTExRUFBNTFFRDU1M0U0REVFRjQxIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6ODA1NjdBMkZCNjIxMTFFQUE1MUVENTUzRTRERUVGNDEiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6ODA1NjdBMzBCNjIxMTFFQUE1MUVENTUzRTRERUVGNDEiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz61pywOAAAErElEQVR42rxUW0xcVRRd987ceTDAQEHKDK9SQAIWeQ4V2tjWpKRa1DS1qUljjKmotX40McaYmPjhn79+NH6p8UcTomliG9IEbfuhlRqrDW1C0xKKwMAwDMzAwLyv69xzp4MVKF+e5Mzcs19r77X3OcpoUkd2pbmbrcCXU3jmwz/xfekOaJYMDAPxo1mABI3mggB1yLPxnAIU0z+jQplZQOazPXjrTC0uTFKmIqevEN/3MxIwyV1Hzb04XF1fwB+rQkG9F4jHpbGFnlYCTsxStsDvYqDWw0TonkpLGzsTmJwH1saAy6dReagY03eYkNVEbKa/GskAS9x5FC5S+OIghqJBFFS7ZVkWyq0Es2uAn0DxZcrzCbICzDC4jYxoqrQTy0MdVoFTgxieYdxS6qNMKJaRelVjigWMXMU9cBWf37uN/SgjvekcFwIssAREQoJXU85qomGZhE172BWkROBSYH4Cja9cxGARj4UWyYQBmFYUNDC9T8eUkxeG8Z4AE5UZTOuSpqUoEJwXvJpguvnP7BcJuMCqHTbkmi1AGefGCI5/fBPnGum3wyoBlQShf4qg9sh5jBtB8qSDt5GJMtMV0jM+ZU6UZgbMLmGfkp81lYCbdC6Shb/HTL3o/xrw7ZvYe7IcI0aFf0XgePkrDCMsMxb8C6MoA4ViBJvgecUEjJlBsjtmAtL+Ae2C9F3JyLMRR5F2r36Ny1dCgmhCfPQHvosnUVvRzOQZVKdDhvtpUhLhMDifoJEtV9CGi4p0AiiwkxVWeavUbAn9FcaZC8D99q8YGjuKLutAK77x7Ea/XYfqpmOGoHnkfIlDEmamFk+Oxiyb+iNn41uA0t5FlloPsUgmrRBwmUYJxjvswnkjtxl6/xCG73oY14mlFtKhiFn+fBP47YoYMfP26th6CT3vVXMv0N9DdkhplJc7TnlfCY6ezsclo4d+Cr1W3DjiRo/oxxL7Eo4A+54icD0touZ0brU12TfXLlbSwfZFJTtsFQ6XoL/OgUtz2Xso+hIiULUDI8fL4NMUrAZj8nV4wWdObdKscrOdkgPX182eE3xuVfbwxE4835SPiwsJeVNgmhvDEGLQGid+P1GOvXYL4uOkp579a9ljTqmy+cCAjNQ30bYGuB+SUV/34GBLPoaCidy1fQiY9ZunstKJ0dcq0OVQsTrOwTnQwsu/cxNQRY6/yof8uTZgalmKznpxoDUPV2cT/51u9VH/eVGpDaMDlfBlkuw7KXq2zaQts8GgkP7eVibl4vMXhf6OF/u6HLg2k9p4ztSNGJrluO6y4c77VehIrmGhglSV11ERXpeuSWVxNdBYZzxv8Xcr0d3uxC9+/d80bgmYFfoJulvD3Q886OTjG2zmwwAHd2Ldk0bDdlLu0hA7U4buHs7AnC6J2KzlKrZQiCe03ooH59zobK1GoOzJdb1kdWUNQFM11vbb4et04tYstgbbEjCrnOau1TD5RiE6XuqEHyUUBOSDwGsTPVAIX5EFowE85vnbDmA2gAjWomD6lBdtvR2YAKe3px2LZ9ljVxq3w+nHA20bMGsUgfFcBY41oKfvGH78pBMHncDdUGr7YEYBuq7j/1z/CDAABvqcFkhM/AYAAAAASUVORK5CYII="},e6b0:function(e,s,t){"use strict";var i=t("fe48"),o=t.n(i);o.a},fe48:function(e,s,t){}}]);