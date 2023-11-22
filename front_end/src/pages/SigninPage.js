import React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button, Card, Form, Container, Row, Col } from "react-bootstrap";
import { changeId } from "../redux/store";
import { useDispatch } from "react-redux";
import { PostAndMoveAndReduxSave } from "../axios/post";

function SigninPage() {
  const [userId, setId] = useState("");
  const [password, setPw] = useState("");
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleIdChange = (e) => {
    setId(e.target.value);
  };

  const handlePwChange = (e) => {
    setPw(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const userData = {
      userId,
      password,
    };

    sendSigninData(userData);
  };

  // 백엔드로 로그인 데이터 보내기
  const sendSigninData = (userData) => {
    const url = process.env.REACT_APP_BACKEND_URL + "user/login";

    let moveUrl = "/main";
    PostAndMoveAndReduxSave(
      url,
      moveUrl,
      userData,
      navigate,
      dispatch,
      changeId,
      userId
    );
  };

  const handleSignup = () => {
    navigate("/signup");
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
                <Row className="justify-content-center">
                  <Col className="pr-1" md="10">
                    <Form.Group>
                      <Col md="1" style={{ padding: "6px" }}>
                        <label htmlFor="id">ID</label>
                      </Col>
                      <Form.Control
                        type="text"
                        id="userId"
                        value={userId}
                        onChange={handleIdChange}
                      ></Form.Control>
                    </Form.Group>
                  </Col>
                </Row>
                <Row className="justify-content-center">
                  <Col className="pr-1" md="10">
                    <Form.Group>
                      <Col md="1" style={{ padding: "6px" }}>
                        <label htmlFor="password">Password</label>
                      </Col>
                      <Form.Control
                        type="password"
                        id="password"
                        value={password}
                        onChange={handlePwChange}
                      ></Form.Control>
                    </Form.Group>
                  </Col>
                </Row>
                <br />
                <Button variant="primary" type="submit">
                  로그인
                </Button>
                <p></p>
                <Button variant="secondary" onClick={handleSignup}>
                  회원가입페이지 이동
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
