import axios from "axios";

function PostAndMove(url, moveUrl, data, navigate) {
  return (
    // JSON 데이터와 함께 POST 요청 보내기
    axios
      .post(url, data, {
        headers: {
          "Content-Type": "application/json", // 데이터가 JSON 형식임을 지정
        },
      })
      .then((response) => {
        console.log(response.data);
        if (response.data === "success") {
          navigate(moveUrl);
        } else {
          alert("실패하였습니다." + response.data);
        }
      })
      .catch((error) => {
        alert("실패 : ", error);
      })
  );
}

function PostAndMoveAndReduxSave(
  url,
  moveUrl,
  data,
  navigate,
  dispatch,
  saveFun,
  saveData
) {
  // JSON 데이터와 함께 POST 요청 보내기
  axios
    .post(url, data, {
      headers: {
        "Content-Type": "application/json", // 데이터가 JSON 형식임을 지정
      },
      withCredentials: false,
    })
    .then((response) => {
      console.log(response.data);
      if (response.data === "success") {
        dispatch(saveFun(saveData));
        navigate(moveUrl);
      } else {
        alert("실패하였습니다. " + response.data);
      }
    })
    .catch((error) => {
      alert("실패 : ", error);
    });
}

export { PostAndMove, PostAndMoveAndReduxSave };
