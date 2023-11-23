import axios from "axios";

function Signup(url, LoginUrl, data, navigate) {
  return (
    // JSON 데이터와 함께 POST 요청 보내기
    axios
      .post(url, data, {
        headers: {
          "Content-Type": "application/json", // 데이터가 JSON 형식임을 지정
        },
      })
      .then((response) => {
        if (response.data === "success") {
          // 회원가입 성공시 로그인 페이지 이동
          navigate(LoginUrl);
        } else {
          console.log("Signup error : " + response.data);
          alert("실패하였습니다. " + response.data);
        }
      })
      .catch((error) => {
        console.log("Signup error : " + error);
        alert("실패 : ", error);
      })
  );
}

function Login(url, mainUrl, data, navigate) {
  // JSON 데이터와 함께 POST 요청 보내기
  axios
    .post(url, data, {
      headers: {
        "Content-Type": "application/json", // 데이터가 JSON 형식임을 지정
      },
      withCredentials: false,
    })
    .then((response) => {
      if (response.data === "success") {
        // 회원가입 성공시
        // LocalStorage 유저 정보 저장
        localStorage.setItem("userId", data.userId);
        // mainUrl로 이동
        navigate(mainUrl);
      } else {
        console.log("Login error : " + response.data);
        alert("실패하였습니다. " + response.data);
      }
    })
    .catch((error) => {
      console.log("Login error : " + error);
      alert("실패 : ", error);
    });
}

export { Signup, Login };
