import React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

// react-bootstrap components
import { Button, Card, Form, Container, Row, Col } from "react-bootstrap";

function SignupPage() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [id, setId] = useState("");
  const [password, setPw] = useState("");
  const navigate = useNavigate();
  // const [userData, setUserData] = useState('');

  const handleNameChange = (e) => {
    setName(e.target.value);
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handleIdChange = (e) => {
    setId(e.target.value);
  };

  const handlePwChange = (e) => {
    setPw(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // 여기에서 name, email, id, pw를 처리하거나 서버로 보낼 수 있습니다.
    console.log("이름:", name);
    console.log("이메일:", email);
    console.log("아이디:", id);
    console.log("비밀번호:", password);

    const userData = {
      name,
      email,
      id,
      password,
    };
    sendSignupData(userData);
  };

  // 백엔드로 회원가입 데이터 보내기
  const sendSignupData = (userData) => {
    navigate("/signin");

    // const url = process.env.REACT_APP_BACKEND_URL + "user/signup";
    // console.log(url);
    // // JSON 데이터와 함께 POST 요청 보내기
    // axios
    //   .post(url, userData, {
    //     headers: {
    //       "Content-Type": "application/json", // 데이터가 JSON 형식임을 지정
    //     },
    //   })
    //   .then((response) => {
    //     console.log(response.data);
    //     if (response.data === "success") {
    //       navigate("/signin");
    //     } else {
    //       alert("회원가입에 실패했습니다.");
    //     }
    //   })
    //   .catch((error) => {
    //     console.error("회원가입 실패 : ", error);
    //   });
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
                          <label htmlFor="name">Name</label>
                        </Col>
                        <Form.Control
                          type="text"
                          id="name"
                          value={name}
                          onChange={handleNameChange}
                        ></Form.Control>
                      </Form.Group>
                    </Col>
                  </Row>
                  <Row className="justify-content-center">
                    <Col className="pr-1" md="10">
                      <Form.Group>
                        <Col md="1" style={{ padding: "6px" }}>
                          <label htmlFor="email">Email</label>
                        </Col>
                        <Form.Control
                          type="email"
                          id="email"
                          value={email}
                          onChange={handleEmailChange}
                        ></Form.Control>
                      </Form.Group>
                    </Col>
                  </Row>
                  <Row className="justify-content-center">
                    <Col className="pr-1" md="10">
                      <Form.Group>
                        <Col md="1" style={{ padding: "6px" }}>
                          <label htmlFor="id">ID</label>
                        </Col>
                        <Form.Control
                          type="text"
                          id="id"
                          value={id}
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
