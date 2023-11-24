import React, { useState, useEffect } from "react";
import { GetData, PostData } from "../axios/mainAxios";

function MainPage() {
  // 입력 불가 단어
  let forbiddenWord = ["<", ">", ":", '"', "/", "\\", "|", "?", "*", " "];

  // localStorage에 저장된 로그인한 유저 ID
  let id = localStorage.getItem("userId");

  // 고정 확장자 목록
  let [fixedList, setFixedList] = useState(
    JSON.parse(localStorage.getItem("fixedList"))
  );

  // 커스텀 확장자 목록
  const [customList, setCustomList] = useState(
    JSON.parse(localStorage.getItem("customList"))
  );
  // 커스텀 확장자 입력 텍스트
  const [customInputText, setCustomInputText] = useState("");
  // 만들어진 커스텀 확장자의 갯수
  const [customListCount, setCustomListCount] = useState(0);

  // 커스텀 확장자 입력시 입력할 수 있는 최대 길이
  const maxInputTextLength = 20;
  // 커스텀 확장자 최대 입력 가능 갯수
  const maxCustomListCount = 200;

  useEffect(() => {
    const Data = async () => {
      try {
        // 고정 확장자 정보 불러오기
        let url =
          process.env.REACT_APP_BACKEND_URL + "fixed/getfixed?userId=" + id;
        let response = await GetData(url, setFixedList);
        if (response.error === null) {
          // 불러온 데이터 저장
          setFixedList(response);
          // 로컬스토리지 저장
          localStorage.setItem("fixedList", JSON.stringify(response));
        } else {
          console.error("GetData error : ", response.error);
          alert("실패 : " + response.error);
        }

        // 커스텀 확장자 정보 불러오기
        url =
          process.env.REACT_APP_BACKEND_URL + "custom/getcustom?userId=" + id;
        response = await GetData(url, setFixedList);
        if (response.error === null) {
          // 불러온 데이터 저장
          setCustomList(response);
          // 로컬스토리지 저장
          localStorage.setItem("customList", JSON.stringify(response));
        } else {
          console.error("GetData error : ", response.error);
          alert("실패 : " + response.error);
        }
        // 커스텀 확장자 갯수 저장
        response && setCustomListCount(response.customName.length);
      } catch (error) {
        console.error("Error in fetchData:", error);
        // 오류 처리를 원하는 방식으로 추가하세요
      }
    };

    Data(); // 비동기 함수 호출
  }, []);

  useEffect(() => {
    // customList가 변경시 갯수 갱신
    customList && setCustomListCount(customList.customName.length);
  }, [customList]);

  const handleInputChange = (e) => {
    // 금지단어 입력불가
    if (forbiddenWord.some((word) => e.target.value.includes(word))) {
      alert(`금지 단어를 입력하셨습니다(금지목록 : ${forbiddenWord})`); // *테스트 하기
    } else {
      // 최대 길이 이상 입력 금지
      if (customInputText.length === maxInputTextLength) {
        alert(
          `더이상 문자를 입력할 수 없습니다(최대 길이 ${maxInputTextLength}자)`
        );
      }
      // customInputText에 변경된 값 저장
      setCustomInputText(e.target.value);
    }
  };
  const CustomAddData = (addData) => {
    // 데이터 추가
    const copyData = [...customList.customName, addData];
    // 배열을 정렬
    copyData.sort();
    // 업데이트할 데이터 생성
    const updatedData = {
      ...customList,
      customName: copyData,
    };
    // 데이터 업데이트합니다.
    setCustomList(updatedData);
  };
  const CustomDeleteData = (deleteData) => {
    const copyData = [...customList.customName];
    // 데이터 삭제
    const filterData = copyData.filter((item) => item !== deleteData);
    // 업데이트할 데이터 생성
    const updatedData = {
      ...customList,
      customName: filterData,
    };
    // 데이터 업데이트합니다.
    setCustomList(updatedData);
  };
  // 커스텀 확장자 추가
  const CreateCustom = async () => {
    // 1. 소문자로 변환
    let lowerCase = customInputText.toLowerCase();
    try {
      if (customListCount === maxCustomListCount) {
        // 2. customListCount가 200 이면 추가 불가능
        alert(
          `더이상 확장자 추가가 불가능 합니다(최대 갯수 ${maxCustomListCount})`
        );
      } else if (lowerCase[0] !== ".") {
        // 3. 마침표로 시작하지 않으면 불가능
        alert("올바른 파일 형식이 아닙니다(마침표로 시작하는지 확인해 주세요)");
      } else if (fixedList.fixedName.some((word) => lowerCase === word)) {
        // 4. 고정확장자에 있으면 생성하지 않는다
        alert("고정 확장자에 있는 확장자입니다");
      } else if (
        customList &&
        customList.customName &&
        customList.customName.some((word) => lowerCase === word)
      ) {
        // 5. 커스텀 확장자 중복 확인
        alert("이미 만들어진 확장자입니다");
      } else {
        //프론트 데이터 추가
        CustomAddData(lowerCase);
        // 입력 초기화
        setCustomInputText("");

        const url = process.env.REACT_APP_BACKEND_URL + "custom/save";
        const data = {
          userId: id,
          name: lowerCase,
        };
        // 서버에 저장 요청
        const response = await PostData(url, data);

        if (response.data === "success") {
          // 성공적으로 저장되었을 때, 업데이트된 커스텀 리스트를 다시 가져온다.
          const updatedUrl =
            process.env.REACT_APP_BACKEND_URL + "custom/getcustom?userId=" + id;
          const updatedCustomList = await GetData(updatedUrl);
          // 로컬 스토리지에 저장
          localStorage.setItem("customList", JSON.stringify(updatedCustomList));
        } else {
          // 미리 추가했던 데이터 삭제
          CustomDeleteData(lowerCase);
          setCustomInputText(lowerCase);
          console.log("PostData error : " + response.data);
          alert("실패 : ", response.data);
        }
      }
    } catch (error) {
      // 실패시 추가했던 데이터 삭제
      CustomDeleteData(lowerCase);
      console.log("error : " + error);
      alert(error);
    }
  };

  // 커스텀 삭제
  const DeleteCustom = async (index) => {
    const deletedText = customList.customName[index];
    try {
      // 프롡트 데이터 삭제
      CustomDeleteData(deletedText);

      const url = process.env.REACT_APP_BACKEND_URL + "custom/delete";
      const data = {
        userId: id,
        name: deletedText,
      };

      // 서버에 삭제 요청
      const response = await PostData(url, data);
      if (response.data === "success") {
        // 삭제 요청이 성공한 경우, 업데이트된 커스텀 리스트를 다시 가져옴
        const updatedUrl =
          process.env.REACT_APP_BACKEND_URL + "custom/getcustom?userId=" + id;
        const updatedCustomList = await GetData(updatedUrl);
        // 커스텀리스트 업데이트
        localStorage.setItem("customList", JSON.stringify(updatedCustomList));
        // setCustomList(updatedCustomList);
      } else {
        // 삭제했던 데이터 생성
        CustomAddData(deletedText);
        console.log("PostData error : " + response.data);
        alert("실패 : ", response.data);
      }
    } catch (error) {
      // 삭제했던 데이터 생성
      CustomAddData(deletedText);
      console.log("error : " + error);
      alert(error);
    }
  };

  // 체크박스 상태 변화
  const handleCheckboxChange = (label, index) => async (e) => {
    let updatedFixed = { ...fixedList };
    try {
      if (e.target.checked) {
        // 프론트 체크 상태 변환
        updatedFixed.fixedStatus[index] = true;
        setFixedList(updatedFixed);

        const url = process.env.REACT_APP_BACKEND_URL + "fixed/savefixtouser";
        const data = {
          userId: id,
          fixedName: label,
        };
        // DB에 저장 요청
        const response = await PostData(url, data);
        if (response.data === "success") {
          const url =
            process.env.REACT_APP_BACKEND_URL + "fixed/getfixed?userId=" + id;
          const updatedFixedList = await GetData(url);
          localStorage.setItem("fixedList", JSON.stringify(updatedFixedList));
        } else {
          updatedFixed.fixedStatus[index] = false;
          setFixedList(updatedFixed);
          console.log("PostData error : " + response.data);
          alert("실패 : ", response.data);
        }
      } else if (!e.target.checked) {
        // 체크 상태 변환
        updatedFixed.fixedStatus[index] = false;
        setFixedList(updatedFixed);

        const url = process.env.REACT_APP_BACKEND_URL + "fixed/deletefixtouser";
        const data = {
          userId: id,
          fixedName: label,
        };
        // DB에 삭제 요청
        const response = await PostData(url, data);
        if (response.data === "success") {
          const url =
            process.env.REACT_APP_BACKEND_URL + "fixed/getfixed?userId=" + id;
          const updatedFixedList = await GetData(url);
          localStorage.setItem("fixedList", JSON.stringify(updatedFixedList));
        } else {
          console.log("PostData error : " + response.data);
          alert("실패 : ", response.data);
        }
      }
    } catch (error) {
      console.log("error : " + error);
      alert(error);
    }

    // fixedList.fixedStatus[index]
    //체크가 되었을떄
  };

  return (
    <div>
      <h3>현재 로그인한 유저 ID : {id}</h3>
      <div>파일 확장자 차단</div>
      <div>
        파일확장자에 따라 특정 형식의 파일을 첨부하거나 전송하지 못하도록 제한
      </div>

      <div className="mb-3">
        <div>
          고정 확장자 <span></span>
          {/* 고정확장자 생성 */}
          {fixedList &&
            fixedList.fixedName.map((label, index) => (
              <label key={label} className="mr-2">
                {label}
                <input
                  type="checkbox"
                  onChange={handleCheckboxChange(label, index)}
                  checked={fixedList.fixedStatus[index]}
                  style={{ marginLeft: "5px" }}
                />
              </label>
            ))}
        </div>
      </div>
      <div>
        <input
          type="text"
          value={customInputText}
          onChange={handleInputChange}
          placeholder="텍스트 입력"
        />
        <button onClick={CreateCustom}>텍스트를 추가</button>
        <div>
          <p>
            고정확장자의 현재 갯수: {customListCount}/{maxCustomListCount}
          </p>
          {/* 커스텀 확장자 생성 */}
          {customList && customList.customName ? (
            customList.customName.map((text, index) => (
              <div
                key={index}
                style={{
                  border: "1px solid #000",
                  padding: "5px",
                  margin: "5px",
                  display: "inline-block",
                }}
              >
                {text}
                <button onClick={() => DeleteCustom(index)}>삭제</button>
              </div>
            ))
          ) : (
            // customList가 존재하지 않거나 customName이 없는 경우에 대한 처리
            <p>고정확장자가 없습니다.</p>
          )}
        </div>
      </div>
    </div>
  );
}

export default MainPage;
