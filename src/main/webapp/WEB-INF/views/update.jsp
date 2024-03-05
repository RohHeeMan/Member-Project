<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>update</title>
</head>
<body>
    <form action="/member/update" method="post" name="updateForm">
        <!-- 수정 불가 처리 readonly -->
        id: <input type="text" name="id" value="${member.id}"readonly>
        <!-- 수정 불가 처리 readonly -->
        <!--email: <input type="text" name="memberEmail" value="${member.memberEmail}"readonly>-->
        email: <input type="text" id = "memberEmail" name="memberEmail" value="${member.memberEmail}">
        password: <input type="text" name="memberPassword" id="memberPassword">
        <!-- 수정 불가 처리 readonly -->
        name: <input type="text" name="memberName" value="${member.memberName}" readonly>
        age: <input type="text" name="memberAge" value="${member.memberAge}">
        mobile: <input type="text" name="memberMobile" value="${member.memberMobile}">
        <input type="button" value="수정" onclick="update()">

    </form>
</div>
</body>
<script>
    const update = () => {
        const passwordDB = '${member.memberPassword}';
        const password = document.getElementById("memberPassword").value;
        if (passwordDB != password) {
            alert("비밀번호가 일치하지 않습니다!");
            return;
        }

        /* 나중에 ajax나 axios이용해서 만들수 있음. 단 현재는 axios를 많이 사용하는 추세임 */
        /* 유효한 이메일을 찾기 위한 자바 스크립트 */
        const email = document.getElementById("memberEmail").value;
        const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailRegex.test(email)) {
            alert("유효한 이메일 주소를 입력하세요.");
            return;
        }

        document.updateForm.submit();
    }
</script>
</html>