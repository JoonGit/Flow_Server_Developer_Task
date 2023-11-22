import axios from "axios";

const GetData = function (url, setFunc) {
  try {
    axios.get(url).then((response) => {
      setFunc(response.data);
    });
  } catch (error) {
    alert(error);
  }
};

function CheckData(url, data) {
  return (
    // JSON 데이터와 함께 POST 요청 보내기
    axios
      .post(url, data, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        console.log(response.data);
        // if (response.data === "success") {
        // } else {
        //   alert("실패하였습니다." + response.data);
        // }
      })
      .catch((error) => {
        alert("실패 : ", error);
      })
  );
}

function PostData(url, data, setfunc) {
  console.log(url);
  return (
    // JSON 데이터와 함께 POST 요청 보내기
    axios
      .post(url, data, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        // if (response.data === "success") {
        // } else {
        //   alert("실패하였습니다." + response.data);
        // }
      })
      .catch((error) => {
        alert("실패 : ", error);
      })
  );
}

export { GetData, PostData };
