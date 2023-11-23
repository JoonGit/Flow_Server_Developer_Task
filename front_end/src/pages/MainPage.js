import React, { useState, useEffect } from "react";
import { GetData, PostData } from "../axios/mainAxios";

function MainPage() {
  // 고정 확장자 목록
  let [fixedList, setFixedList] = useState();

  // 입력 불가 단어
  let forbiddenWord = ["<", ">", ":", '"', "/", "\\", "|", "?", "*", " "];

  // Redux에 저장된 로그인한 유저 ID
  // let id = useSelector((state) => state.userId);
  let id = localStorage.getItem("userId");

  // 커스텀 확장자 입력 텍스트
  const [customInputText, setCustomInputText] = useState("");

  // 커스텀 확장자 입력시 입력할 수 있는 최대 길이
  const maxInputTextLength = 20;

  // 커스텀 확장자 이름 목록
  const [customList, setCustomList] = useState();
  // 만들어진 커스텀 확장자의 갯수
  const [customListCount, setCustomListCount] = useState(0);

  // 최대 입력 가능 갯수
  const maxCustomListCount = 200;

  useEffect(() => {
    // 고정 확장자 정보 불러오기
    let url = process.env.REACT_APP_BACKEND_URL + "fixed/getfixed?userId=" + id;
    GetData(url, setFixedList);

    // 커스텀 확장자 정보 물러오기
    url = process.env.REACT_APP_BACKEND_URL + "custom/getcustom?userId=" + id;
    GetData(url, setCustomList);

    // 커스텀 확장자 갯수 저장
    customList && setCustomListCount(customList.customName.length);
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

  // 커스텀 확장자 추가
  const createDiv = () => {
    // 1.소문자로 변환
    let lowerCase = customInputText.toLowerCase();

    if (customListCount === maxCustomListCount) {
      // 2.customListCount가 200 이면 추가 불가능
      alert(
        `더이상 확장자 추가가 불가능 합니다(최대 갯수${maxCustomListCount})`
      );
      // 3.마침표으로 시작하지 않으면 불가능
    } else if (lowerCase[0] !== ".") {
      alert("올바른 파일 형식이 아닙니다(마침표로 시작하는지 확인해 주세요)");
    }
    // 4.고장확장자에 있으면 생성하지 않는다
    else if (fixedList.fixedName.some((word) => lowerCase === word)) {
      alert("고정 확장자에 있는 확장자 입니다");
    }
    // 5.커스텀 확장자 중복 확인
    else if (
      customList &&
      // customList.customName && // 테스트 필요
      customList.customName.some((word) => lowerCase === word)
    ) {
      alert("이미 만들어진 확장자 입니다");
    } else {
      const url = process.env.REACT_APP_BACKEND_URL + "custom/save";
      const data = {
        userId: id,
        name: lowerCase,
      };
      try {
        // DB 저장 요청
        PostData(url, data).then(() => {
          const url =
            process.env.REACT_APP_BACKEND_URL + "custom/getcustom?userId=" + id;
          GetData(url, setCustomList);
        });
        // 입력 필드 초기화
        setCustomInputText("");
      } catch (error) {
        alert(error);
      }
    }
  };

  // 커스텀 삭제
  const deleteDiv = (index) => {
    // 삭제될 커스텀 확장자
    const deletedText = customList.customName[index];

    const url = process.env.REACT_APP_BACKEND_URL + "custom/delete";
    const data = {
      userId: id,
      name: deletedText,
    };
    // 삭제 요청
    try {
      PostData(url, data).then(() => {
        const url =
          process.env.REACT_APP_BACKEND_URL + "custom/getcustom?userId=" + id;
        GetData(url, setCustomList);
      });
    } catch (error) {
      alert(error);
    }
  };

  // 체크박스 상태 변화
  const handleCheckboxChange = (label) => (e) => {
    const data = {
      userId: id,
      fixedName: label,
    };

    //체크가 되었을떄
    if (e.target.checked) {
      const url = process.env.REACT_APP_BACKEND_URL + "fixed/savefixtouser";
      // DB에 저장 요청
      PostData(url, data)
        .then(() => {
          // 저장된 정보 불러오기
          const url =
            process.env.REACT_APP_BACKEND_URL + "fixed/getfixed?userId=" + id;
          GetData(url, setFixedList);
        })
        .catch((error) => {
          console.error("오류 발생:", error);
        });
    } else if (!e.target.checked) {
      const url = process.env.REACT_APP_BACKEND_URL + "fixed/deletefixtouser";
      // DB에 삭제 요청
      PostData(url, data)
        .then(() => {
          // 저장된 정보 불러오기
          const url =
            process.env.REACT_APP_BACKEND_URL + "fixed/getfixed?userId=" + id;
          GetData(url, setFixedList);
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
          {/* 고정확장자 생성 */}
          {fixedList &&
            fixedList.fixedName.map((label, index) => (
              <label key={label} className="mr-2">
                {label}
                <input
                  type="checkbox"
                  onChange={handleCheckboxChange(label)}
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
        <button onClick={createDiv}>텍스트를 추가</button>
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
                <button onClick={() => deleteDiv(index)}>삭제</button>
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
