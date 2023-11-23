import React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Signup } from "../axios/userAxios";

// react-bootstrap components
import { Button, Card, Form, Container, Row, Col } from "react-bootstrap";

function SignupPage() {
  const [userId, setUserId] = useState("");
  const [password, setPw] = useState("");
  const navigate = useNavigate();

  const handleIdChange = (e) => {
    setUserId(e.target.value);
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
    sendSignupData(userData);
  };

  // 백엔드로 회원가입 데이터 보내기
  const sendSignupData = (userData) => {
    if (userData.userId.trim() === "" || userData.password.trim() === "") {
      return alert("userId 또는 password가 공백입니다.");
    }
    const url = process.env.REACT_APP_BACKEND_URL + "user/signup";
    const moveUrl = "/";
    Signup(url, moveUrl, userData, navigate);
  };

  return (
    <>
      <Container fluid className="text-center">
        <Row>
          <Col md="6">
            <Card>
              <Card.Header>
                <Card.Title as="h4">회원가입</Card.Title>
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
                  {/* <Button onClick={handleSubmit}></Button> */}
                  <Button variant="primary" type="submit">
                    회원가입
                  </Button>
                </Form>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </>
  );
}

export default SignupPage;
