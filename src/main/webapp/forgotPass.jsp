<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forgot Pass</title>
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    <link rel="stylesheet" href="assets/css/form.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
</head>

<body style="background: url('img/pxfuel.jpg') no-repeat; background-size: cover">
<div class="form">
    <div class="form-box login">
        <h2>Quên Mật Khẩu</h2>
        <div class="input-box">
            <span class="icon"></span>
            <input type="email" name="email" id="emailInput" placeholder="Nhập gmail của bạn..." required>
            <label>Email</label>
            <div id="emailValidationMessage"
                 style="  margin: 10px 0px 7px;color: red; font-size: 13px;font-weight: 700"></div>
        </div>
        <button id="btnSendMail" class="btn btn-success" disabled>Gửi Mail</button>
        <div class="login-register">
            <p>Bạn đã có tài khoản?
                <a href="login.jsp" class="register-link">Đăng nhập</a>
            </p>
        </div>
    </div>
</div>

</body>

<script src="assets/js/forgotPass.js"></script>

</html>
