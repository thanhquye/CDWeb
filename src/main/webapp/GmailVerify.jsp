<%--
  Created by IntelliJ IDEA.
  User: rrioz
  Date: 12/6/2023
  Time: 1:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");
    Boolean resent = (Boolean) request.getAttribute("resent");
    String reason = (String) request.getAttribute("reason");

    Long lastSentTime = (Long) session.getAttribute("lastVerificationEmailTime");
    long now = System.currentTimeMillis();
    boolean allowResend = (lastSentTime == null || now - lastSentTime >= 60 * 1000);

    // Nếu chưa đủ 1 phút thì tính số giây còn thiếu để hiển thị đếm ngược
    long secondsLeft = 0;
    if (!allowResend && lastSentTime != null) {
        long diffMillis = 60 * 1000 - (now - lastSentTime);
        secondsLeft = diffMillis / 1000;
    }
%>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Xác thực tài khoản</title>
    <link rel="icon" type="image/x-icon" href="assets/images/x-icon.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="assets/css/style.css">
    <%--    <link rel="stylesheet" href="assets/css/style_new.css">--%>
    <link rel="stylesheet" href="assets/css/contact.css">
    <link rel="stylesheet" href="./assets/fonts/fontawesome-free-6.5.1/css/all.min.css">
    <link href="//fonts.googleapis.com/css2?family=Open+Sans:ital,wght@0,300;0,400;0,600;0,700;1,600&display=swap"
          rel="stylesheet">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.min.css"
          integrity="sha512-sMXtMNL1zRzolHYKEujM2AqCLUR9F2C4/05cdbxjjLSRvMQIciEPCQZo++nk7go3BtSuK9kfa/s+a4f4i5pLkw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css"
          integrity="sha512-tS3S5qG0BlhnQROyJXvNjeEM4UpMXHrQfTGmbQ1gKmelCxlSEBUaxhRBj/EFTzpbP4RVSrpEikbmdJobCvhE3g=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css_made/gmailVerify.css">

</head>
<body>
<%-- Header --%>
<jsp:include page="layout-view/header.jsp"></jsp:include>

<%-- Breadcrumbs --%>
<div class="w3l-breadcrumbs">
    <nav id="breadcrumbs" class="breadcrumbs">
        <div class="container page-wrapper">
            <a href="index.jsp">Home</a> » <span class="breadcrumb_last" aria-current="page">Xác Thực Gmail</span>
        </div>
    </nav>
</div>

<%-- GMAIL VERIFY SECTION --%>
<section class="ftco-section contact-section">
    <div class="gmail-verify-wrapper">
        <div class="container">
            <div class="row d-flex mb-5 contact-info">
                <div class="container">
                    <h2>
                        Tài khoản <span class="email"><%= user.getUserName() %></span> với Gmail là
                        <span class="email"><%= user.getEmail() %></span> chưa được xác minh
                    </h2>
                    <p>Vui lòng bấm nút "Gửi mã xác minh" và kiểm tra gmail của bạn để xác minh tài khoản.</p>

                    <% if (resent != null && resent) { %>
                    <div class="message" style="color: green;">Mã xác minh đã được gửi!</div>
                    <% } else if (resent != null && !resent && reason != null) { %>
                    <div class="message" style="color: red;"><%= reason %>
                    </div>
                    <% } %>

                    <%-- Các nút gửi lại mã xác minh và đếm ngược --%>
                    <% if (allowResend) { %>
                    <a id="resendButton" class="resend-link"
                       href="<%= request.getContextPath() %>/resendVerification?email=<%= user.getEmail() %>">
                        <%= (lastSentTime == null) ? "Gửi mã xác minh" : "Gửi lại mã xác minh" %>
                    </a>
                    <% } else { %>
                    <a id="resendButton" class="resend-link"
                       href="<%= request.getContextPath() %>/resendVerification?email=<%= user.getEmail() %>"
                       style="display: none;">
                        Gửi lại mã xác minh
                    </a>
                    <p style="color: rgb(128,128,128);" id="resendTimer">
                        Bạn phải đợi ít nhất 1 phút trước khi gửi lại mã xác minh. Vui lòng chờ
                        <span id="timer"><%= secondsLeft %></span> giây.
                    </p>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="assets/js/gmailVerify.js"></script>

<%-- Footer --%>
<jsp:include page="layout-view/footer.jsp"></jsp:include>
<%-- Scripts --%>
<script src="assets/js/main.js"></script>
<script src="assets/js/contact.js"></script>
<script src="assets/js/theme-change.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"
        integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
        integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"
        integrity="sha512-bPs7Ae6pVvhOSiIcyUClR7/q2OAsRiovw4vAkX+zJbw3ShAeeqezq50RIIcIURq7Oa20rW2n2q+fyXBNcU9lrw=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>