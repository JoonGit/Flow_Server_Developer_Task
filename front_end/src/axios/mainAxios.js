import axios from "axios";

const GetData = async (url) => {
  try {
    const response = await axios.get(url);
    return response.data;
  } catch (error) {
    console.error("GetData error : ", error);
    alert("실패 : " + error.message);
  }
};

const PostData = async (url, data) => {
  try {
    const response = await axios.post(url, data, {
      headers: {
        "Content-Type": "application/json",
      },
    });
    return response;
  } catch (error) {
    console.log("PostData error : " + error);
    alert("실패 : ", error);
  }
};

export { GetData, PostData };
