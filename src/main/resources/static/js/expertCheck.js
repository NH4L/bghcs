var home = "/bghcs";
var expertStorage = window.localStorage;

function checkNameReg(theObj) {//正则判断姓名是否合格
    var reg = /^[\u4e00-\u9fa5]{2,4}$/;
    if (reg.test(theObj)) {
        return true;
    }
    return false;
}

function checkEmailReg(theObj) {//正则判断邮箱是否合格
    var reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
    if(reg.test(theObj)){
        return true;
    }
    return false;
}

function checkPasswordReg(theObj) {//正则判断密码是否合格
    var reg = /^.*(?=.{6,20})(?=.*\d)(?=.*[a-z])(?=.*[_@#$%^&*? ]).*$/;
    if(reg.test(theObj)){
        return true;
    }
    return false;
}

function checkExpertName() {//检查姓名是否合适
    var name = $("#input_expertName").val()
    if(name == "" || name == null) {
        $("#input_expertNameWarn").html("姓名不能为空！");
        return false;
    } else if (!checkNameReg(name)) {
        $("#input_expertNameWarn").html("姓名必须为2-4个汉字");
        return false;
    } else {
        $("#input_expertNameWarn").html("");
        return true
    }
}

function checkExpertEmail() {//检查邮箱是否合适
    var email = $("#input_expertEmail").val();
    if(email == "" || email == null) {
        $("#input_expertEmailWarn").html("邮箱不能为空！");
        return false;
    } else if (!checkEmailReg(email)) {
        $("#input_expertEmailWarn").html("邮箱非法！");
        return false;
    } else {
        $("#input_expertEmailWarn").html("");
        return true
    }
}

function checkExpertPassword() {//检查密码是否合适
    var password = $("#input_expertPassword").val();
    if(password == "" || password == null) {
        $("#input_expertPasswordWarn").html("密码不能为空！");
        return false;
    } else if (!checkPasswordReg(password)) {
        $("#input_expertPasswordWarn").html("密码强度较弱，必须由字母，数字，特殊字符(!@#$%^&*?)组成");
        return false;
    } else {
        $("#input_expertPasswordWarn").html("");
        return true
    }
}

function checkExpertEducation() {//检查学历是否合适
    var education = $("#input_expertEducation").val();
    if(education == "" || education == null) {
        $("#input_expertEducationWarn").html("学历不能为空！");
        return false;
    } else if (!checkNameReg(education)) {
        $("#input_expertEducationWarn").html("学历填写需符合规范！");
        return false;
    } else {
        $("#input_expertEducationWarn").html("");
        return true
    }
}

function checkExpertJobTitle() {//检查职称是否合适
    var jobTitle = $("#input_expertJobTitle").val();
    if(jobTitle == "" || jobTitle == null) {
        $("#input_expertJobTitleWarn").html("职称不能为空！");
        return false;
    } else if (!checkNameReg(jobTitle)) {
        $("#input_expertJobTitleWarn").html("职称填写需符合规范！");
        return false;
    } else {
        $("#input_expertJobTitleWarn").html("");
        return true
    }
}

function register() {
    if ((!checkExpertName()) || (!checkExpertEmail()) || (!checkExpertPassword()) || (!checkExpertEducation()) || (!checkExpertJobTitle())) {
        return false;
    }
    var expertEmail = $("#input_expertEmail").val();
    var expertPassword = $("#input_expertPassword").val();
    var expertName = $("#input_expertName").val();
    var expertEducation = $("#input_expertEducation").val();
    var expertJobTitle = $("#input_expertJobTitle").val();

    var obj = {
        "expertEmail":expertEmail,
        "expertPassword": expertPassword,
        "expertName": expertName,
        "expertEducation": expertEducation,
        "expertJobTitle": expertJobTitle
    };
    $(function() {
        $.ajax({
            type: "POST",
            dataType: "json",
            url: home + "/expert/register",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(obj),
            success: function (data) {
                console.log(data)
                var isSuccess = data['msg']
                if (isSuccess == "success") {
                    alert("注册成功");
                    window.location.href=home + "/login";
                } else if (isSuccess == "emailExists") {
                    alert("邮箱已被注册，请更换");
                    $("#input_expertEmail").val("");
                    return false;
                } else {
                    alert("注册失败，请稍后重试");
                    return false;
                }
            },
            error: function () {
                alert("访问繁忙，请重试")
            }
        });
    });
    return false;
}

function checkExpertEmailLogin() {//检查邮箱是否合适
    var email = $("#input_expertEmail").val()
    if(email == "" || email == null) {
        $("#input_expertEmailWarn").html("邮箱不能为空！")
        return false;
    } else if (!checkEmailReg(email)) {
        $("#input_expertEmailWarn").html("邮箱有误！")
        return false;
    } else {
        $("#input_expertEmailWarn").html("")
        return true
    }
}

function checkExpertPasswordLogin() {//检查密码是否合适
    var password = $("#input_expertPassword").val()
    if(password == "" || password == null) {
        $("#input_expertPasswordWarn").html("密码不能为空！");
        return false;
    } else if (!checkPasswordReg(password)) {
        $("#input_expertPasswordWarn").html("密码有误！");
        return false;
    } else {
        $("#input_expertPasswordWarn").html("");
        return true
    }
}

function login() {
    if ((!checkExpertEmailLogin()) || (!checkExpertPasswordLogin())) {
        return false;
    }
    var expertEmail = $("#input_expertEmail").val();
    var expertPassword = $("#input_expertPassword").val();
    
    $(function() {
        $.ajax({
            type: "POST",
            dataType: "json",
            url: home + "/expert/login",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            data: "expertEmail=" + expertEmail + "&expertPassword=" + expertPassword,
            success: function (data) {
                console.log(data);
                var isSuccess = data['msg'];
                if (isSuccess == "success") {
                    var expert = data['data'];
                    console.log(expert);

                    var expertStorage = window.localStorage;
                    expertStorage["expertName"] = expert['expertName'];
                    expertStorage["expertEmail"] = expert['expertEmail'];
                    expertStorage["expertEducation"] = expert['expertEducation'];
                    expertStorage["expertJobTitle"] = expert['expertJobTitle'];

                    alert("登录成功");
                    window.location.href=home + "/index";
                    return true;
                } else if(isSuccess == "fail") {
                    alert("账号或密码错误，请重试");
                    $('#input_expertPassword').val("");
                    return false;
                }
            },
            error: function () {
                alert("访问繁忙，请重试")
            }
        });
    });
    return false;
}

function changeInfo() {
    if ((!checkExpertName()) || (!checkExpertEducation()) || (!checkExpertJobTitle())) {
        return false;
    }


    var expertEmail = $("#input_expertEmail").val();
    var expertName = $("#input_expertName").val();
    var expertEducation = $("#input_expertEducation").val();
    var expertJobTitle = $("#input_expertJobTitle").val();
    if ((expertName==expertStorage['expertName']) &&(expertEducation==expertStorage['expertEducation'])
        &&(expertJobTitle==expertStorage['expertJobTitle'])) {
        alert("请修改后提交!")
        return false;
    }
    var obj = {
        "expertEmail":expertEmail,
        "expertName": expertName,
        "expertEducation": expertEducation,
        "expertJobTitle": expertJobTitle
    };
    $(function() {
        $.ajax({
            type: "POST",
            dataType: "json",
            url: home + "/expert/change",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(obj),
            success: function (data) {
                console.log(data);
                var isSuccess = data['msg'];
                if (isSuccess == "success") {
                    expertStorage["expertName"] = expertName;
                    expertStorage["expertEmail"] = expertEmail;
                    expertStorage["expertEducation"] = expertEducation;
                    expertStorage["expertJobTitle"] = expertJobTitle;

                    alert("修改信息成功");

                    $('#left_expertName').html(expertStorage["expertName"]);
                    $('#left_expertJobTitle').html(expertStorage["expertJobTitle"]);
                    $('#IndexExpertName').html(expertStorage["expertName"]);
                    return false;
                } else {
                    alert("修改失败，请稍后重试");
                    return false;
                }
            },
            error: function () {
                alert("访问繁忙，请重试")
            }
        });
    });
    return false;
}

function expertLogout() {
    expertStorage.clear();
    window.location.href= home + "/login";
}

function checkCropNameReg(theObj) {//正则判断姓名是否合格
    var reg = /^[\u4e00-\u9fa5]{1,}$/;
    if (reg.test(theObj)) {
        return true;
    }
    return false;
}

function checkCropName() {//检查农作物名称是否合适
    var cropName = $("#input_cropName").val();
    if(cropName == "" || cropName == null) {
        $("#input_cropNameWarn").html("农作物名称不能为空！");
        return false;
    } else if (!checkCropNameReg(cropName)) {
        $("#input_cropNameWarn").html("农作物名称不规范");
        return false;
    } else {
        $("#input_cropNameWarn").html("");
        return true
    }
}

function checkDiseaseName() {//检查病虫害名称是否合适
    var diseaseName = $("#input_diseaseName").val();
    if(diseaseName == "" || diseaseName == null) {
        $("#input_diseaseNameWarn").html("病虫害名称不能为空！");
        return false;
    } else if (!checkCropNameReg(diseaseName)) {
        $("#input_diseaseNameWarn").html("病虫害名称不规范");
        return false;
    } else {
        $("#input_diseaseNameWarn").html("");
        return true;
    }
}


