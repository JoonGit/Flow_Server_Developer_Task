import React from "react";
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Button, Card, Form, Container, Row, Col } from "react-bootstrap";
import { changeId } from "../redux/store";
import { useDispatch } from "react-redux";

function SigninPage() {
  const [id, setId] = useState("");
  const [pw, setPw] = useState("");
  const navigate = useNavigate();
  let dispatch = useDispatch();

  const handleIdChange = (e) => {
    setId(e.target.value);
  };

  const handlePwChange = (e) => {
    setPw(e.target.value);
  };

  const handleSubmit = (e) => {
    const data = {
      id: id,
      password: pw,
    };
    // POST 요청을 보낼 엔드포인트 URL
    const url = process.env.REACT_APP_BACKEND_URL + "user/signin";
    dispatch(changeId(id));
    navigate("/main");
    // JSON 데이터와 함께 POST 요청 보내기
    // axios
    //   .post(url, data, {
    //     headers: {
    //       "Content-Type": "application/json", // 데이터가 JSON 형식임을 지정
    //     },
    //   })
    //   .then((response) => {
    //     console.log("토큰 : .", response.data);
    //     if (response.data === "success") {
    //       dispatch(changeId(id));

    //       navigate("/main");
    //     } else {
    //       alert("로그인에 실패했습니다.");
    //     }
    //   })
    //   .catch((error) => {
    //     console.error("로그인 실패 : ", error);
    //   });
  };
  return (
    <Container fluid className="text-center">
      <Row>
        <Col md={{ span: 6, offset: 3 }}>
          <Card>
            <Card.Header>
              <Card.Title as="h4">로그인</Card.Title>
            </Card.Header>
            <Card.Body>
              <Form onSubmit={handleSubmit}>
                <Form.Group controlId="formBasicEmail">
                  <Form.Label>ID</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Enter Id"
                    value={id}
                    onChange={handleIdChange}
                  />
                </Form.Group>
                <Form.Group controlId="formBasicPassword">
                  <Form.Label>비밀번호</Form.Label>
                  <Form.Control
                    type="password"
                    placeholder="Password"
                    value={pw}
                    onChange={handlePwChange}
                  />
                </Form.Group>
                <Button variant="primary" type="submit">
                  로그인
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}

export default SigninPage;
