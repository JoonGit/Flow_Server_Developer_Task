import axios from "axios";

const GetData = function (url, setFunc) {
  try {
    axios.get(url).then((response) => {
      console.log(response.data);
      setFunc(response.data);
    });
  } catch (error) {
    console.log("GetData error : " + error);
    alert("실패 : ", error);
  }
};

function PostData(url, data) {
  try {
    axios.post(url, data, {
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
