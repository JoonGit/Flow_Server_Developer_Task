import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { GetData, PostData } from "../axios/mainAxios";

// 유저 정보는 로그인시 redux에 저장되서 가져오기
// 페이지가 불러올 때 WAS에서
// 해당 유저의 id에 해당하는 정보 가져오기
// 1. 고정확장자 checkList
// 2. 커스텀 확장서 List
// 3. 안전확장자 리스트 가져오기
// 페이지에 불어와 지면 항상 동일한 상태가 되도록 하기

function MainPage() {
  let dispatch = useDispatch();

  // DB 에서 고정 목록 가져올 예정
  let [fixedExtensionList, setFixedExtensionList] = useState();
  let forbiddenWord = ["<", ">", ":", '"', "/", "\\", "|", "?", "*", " "];
  // let id = useSelector((state) => state.userId); // 로그인한 유저 ID
  let id = "user";

  const [inputText, setInputText] = useState(""); // 입력된 텍스트 관리
  const maxInputTextLength = 20;

  // DB에서 커스텀으로 만들어진 목록 가져올 예정
  const [customList, setCustomList] = useState(); // 생성된 div 목록 관리
  const [customListLength, setCustomListLength] = useState(0); // divList의 현재 갯수 상태 관리
  const maxCustomListLength = 200;

  useEffect(() => {
    // const url = process.env.REACT_APP_BACKEND_URL + "extension/getinfo?" + id;
    let url = process.env.REACT_APP_BACKEND_URL + "fixed/getfixed?userId=" + id;
    GetData(url, setFixedExtensionList);
    url = process.env.REACT_APP_BACKEND_URL + "custom/getcustom?userId=" + id;
    GetData(url, setCustomList);
    customList && setCustomListLength(customList.customExtensionName.length);
    // 1. 고정확장자 checkList
    // 2. 커스텀 확장자 List
  }, []);

  useEffect(() => {
    customList && setCustomListLength(customList.customExtensionName.length); // divList의 길이 변경 시 divListLength 갱신
    console.log("변경후 실행");
  }, [customList]);

  const handleInputChange = (e) => {
    // 금지단어 입력불가
    if (forbiddenWord.some((word) => e.target.value.includes(word))) {
      alert(
        '금지 단어를 입력하셨습니다(금지목록 : <, >, :, ", /, \\, |, ?, *)'
      );
    } else {
      // 최대 길이 이상 입력 금지
      if (inputText.length === maxInputTextLength) {
        alert(
          `더이상 문자를 입력할 수 없습니다(최대 길이 ${maxInputTextLength}자)`
        );
      }
      setInputText(e.target.value); // input의 변경된 값 설정
    }
  };

  // 커스텀이 생성 되었을때
  const createDiv = () => {
    let lowerCase = inputText.toLowerCase();
    if (customListLength === maxCustomListLength) {
      // divListLength가 200 이면 추가 불가능
      alert(
        `더이상 확장자 추가가 불가능 합니다(최대 갯수${maxCustomListLength})`
      );
    } else if (lowerCase[0] !== ".") {
      // 마침표으로 시작하지 않으면 불가능
      alert("올바른 파일 형식이 아닙니다(마침표로 시작하는지 확인해 주세요)");
    } else if (
      fixedExtensionList.fixedExtensionName.some((word) => lowerCase === word)
    ) {
      // 고장확장자에 있으면 생성하지 않는다
      alert("고정 확장자에 있는 확장자 입니다");
    } else if (
      customList &&
      customList.customExtensionName &&
      customList.customExtensionName.some((word) => lowerCase === word)
    ) {
      // 커스텀 확장자에 있으면 생성하지 않는다
      alert("이미 만들어진 확장자 입니다");
    } else {
      // 공백이면 넣지 않는다
      // was로 전송할 코드 생성
      const url = process.env.REACT_APP_BACKEND_URL + "custom/save";
      const data = {
        userId: id,
        name: lowerCase,
      };
      PostData(url, data).then(() => {
        const url =
          process.env.REACT_APP_BACKEND_URL + "custom/getcustom?userId=" + id;
        GetData(url, setCustomList);
      });

      customList && setCustomListLength(customList.customExtensionName.length);
      // setCustomList([...customList, lowerCase]);
      setInputText("");
      //성공시 div로 데이터 만들기
      // 기존의 div 목록에 새로운 div 추가
      // 입력 필드 초기화
    }
  };

  // 커스텀 삭제
  const deleteDiv = (index) => {
    // 삭제시 안내문구 만들기
    console.log();
    const deletedText = customList.customExtensionName[index]; // 삭제될 div의 text
    console.log(`Deleted text: ${deletedText}`); // 삭제될 div의 text를 출력
    // const updatedDivList = customList.customExtensionName.filter(
    //   (_, i) => i !== index
    // );
    // 삭제할 div를 제외한 목록

    const url = process.env.REACT_APP_BACKEND_URL + "custom/delete";
    const data = {
      userId: id,
      name: deletedText,
    };
    PostData(url, data).then(() => {
      const url =
        process.env.REACT_APP_BACKEND_URL + "custom/getcustom?userId=" + id;
      GetData(url, setCustomList);
      setCustomListLength(customList.customExtensionName.length);
    });
    // 변경된 div 목록 설정
  };

  const handleCheckboxChange = (label) => (e) => {
    const data = {
      userId: id,
      extensionName: label,
    };

    //체크가 되었을떄
    if (e.target.checked) {
      const url = process.env.REACT_APP_BACKEND_URL + "fixed/savefixtouser";
      PostData(url, data)
        .then(() => {
          const url =
            process.env.REACT_APP_BACKEND_URL + "fixed/getfixed?userId=" + id;
          GetData(url, setFixedExtensionList);
        })
        .catch((error) => {
          console.error("오류 발생:", error);
        });
    } else if (!e.target.checked) {
      const url = process.env.REACT_APP_BACKEND_URL + "fixed/deletefixtouser";
      PostData(url, data)
        .then(() => {
          const url =
            process.env.REACT_APP_BACKEND_URL + "fixed/getfixed?userId=" + id;
          GetData(url, setFixedExtensionList);
        })
        .catch((error) => {
          console.error("오류 발생:", error);
        });
    }
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
          {fixedExtensionList &&
            fixedExtensionList.fixedExtensionName.map((label, index) => (
              <label key={label} className="mr-2">
                {label}
                <input
                  type="checkbox"
                  onChange={handleCheckboxChange(label)}
                  checked={fixedExtensionList.fixedExtensionStatus[index]}
                  style={{ marginLeft: "5px" }}
                />
              </label>
            ))}
        </div>
      </div>
      <div>
        <input
          type="text"
          value={inputText}
          onChange={handleInputChange}
          placeholder="텍스트 입력"
        />
        <button onClick={createDiv}>텍스트를 추가</button>
        <div>
          <p>
            고정확장자의 현재 갯수: {customListLength}/{maxCustomListLength}
          </p>
          {customList && customList.customExtensionName ? (
            customList.customExtensionName.map((text, index) => (
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
                <button onClick={() => deleteDiv(index)}>삭제</button>
              </div>
            ))
          ) : (
            // customList가 존재하지 않거나 customExtensionName이 없는 경우에 대한 처리
            <p>고정확장자가 없습니다.</p>
          )}
        </div>
      </div>
    </div>
  );
}

export default MainPage;
