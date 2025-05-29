<%--
  Created by IntelliJ IDEA.
  User: QUYEN
  Date: 02/12/2023
  Time: 14:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    <link rel="stylesheet" href="assets/css/form.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

    <link rel="stylesheet" href="assets/css_made/register.css">
</head>

<body style="background: url('img/pxfuel.jpg') no-repeat; background-size: cover">
<input type="hidden" id="status" value="<%= request.getAttribute("status")%>">
<div class="form register">
    <div class="form-box register">
        <h2>Đăng ký</h2>
        <form id="form" action="register" method="post">
            <div class="input-box">
                <span class="icon"><ion-icon name="person"></ion-icon></span>
                <input type="text" id="userName" name="userName" required>
                <label>Tên người dùng:</label>
                <small></small>
            </div>
            <div class="input-box">
                <span class="icon"><ion-icon name="mail-open"></ion-icon></span>
                <input type="email" id="email" name="email" required>
                <label>Email</label>
                <small></small>
            </div>

            <div class="input-box">
                <span class="icon"><ion-icon name="lock-closed"></ion-icon></span>
                <input type="password" id="password" name="password" required>
                <label>Mật khẩu:</label>
                <small></small>
            </div>
            <div class="input-box">
                <span class="icon"><ion-icon name="lock-closed"></ion-icon></span>
                <input type="password" id="retypePassword" name="retypePassword" required>
                <label>Nhập lại mật khẩu:</label>
                <small></small>
            </div>
            <%--            <div class="remenber-forgot">--%>
            <%--                <label>--%>
            <%--                    <input type="checkbox" required> I agree to the terms &amp; conditions--%>
            <%--                </label>--%>
            <%--            </div>--%>
            <button type="submit" id="registerBtn" class="btn btn-success" disabled>Register</button>

            <div class="login-register">
                <p>Đã có tài khoản?
                    <a href="login.jsp" class="login-link">Đăng nhập</a>
                </p>
            </div>

        </form>
    </div>
</div>

</body>

<script src="assets/js/register.js"></script>

</html>
