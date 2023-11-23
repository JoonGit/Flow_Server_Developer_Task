import axios from "axios";

const GetData = function (url, setFunc) {
  try {
    axios.get(url).then((response) => {
      if (response.data.error === null) {
        setFunc(response.data);
      } else {
        console.log("GetData error : " + response.data.error);
        alert("실패 : ", response.data.error);
      }
    });
  } catch (error) {
    console.log("GetData error : " + error);
    alert("실패 : ", error);
  }
};

function PostData(url, data) {
  try {
    return axios.post(url, data, {
      headers: {
        "Content-Type": "application/json",
      },
    });
  } catch (error) {
    console.log("PostData error : " + error);
    alert("실패 : ", error);
  }
}

export { GetData, PostData };
