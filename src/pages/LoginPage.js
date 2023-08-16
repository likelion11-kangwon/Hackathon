import { NavLink } from "react-router-dom";
import React, { useState } from 'react';
import './LoginPage.css';

const LoginPage = () => {
  // 상태변수 선언
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  // const [loginSuccess, setLoginSuccess] = useState(false);
  const [loginError, setLoginError] = useState('');
  

  // 로그인 버튼 클릭시
  const handleLogin = async (e) => {
        e.preventDefault();

        // 아이디 입력 안했을 때
        if (id === '') {
              setLoginError('아이디를 입력해주세요.');
              return;
        }
        // 비밀번호 입력 안했을 때
        else if (password === '') {
              setLoginError('비밀번호를 입력해주세요.');
              return;
        }

        console.log(`id: ${id}, Password: ${password}`);
        if (id === '1234' && password === 'qwer') {
              console.log('로그인 성공');
              setLoginError('');
            } else {
              console.log('로그인 실패');
              setLoginError('아이디 또는 비밀번호를 잘못 입력했습니다.');
            }
            
  };

  //아이디랑 비밀번호 입력값을 지우는 함수
  const handleClearId = () => {
        setId('');
  };
  const handleClearPassword = () => {
        setPassword('');
  };
  

return (
  <div className="container">
         <NavLink to="/"><div className="title"> AILION </div></NavLink>
        <form onSubmit={handleLogin} className="login_form">
              <label className="login_label">
                    <input 
                        className="login_input"
                        type="text"  
                        placeholder="아이디" 
                        id="username"
                        value={id} 
                        onChange={(e) => setId(e.target.value)} 
                    />
                    {id && <div type="button" className="btn_clear" onClick={handleClearId}>
                          <img className="btn_clear_img" alt="close_icon" src="img/clear.png" />
                          </div>}
              </label>
              <label>
                    <input 
                        className="login_input"
                        type="password" 
                        placeholder="비밀번호" 
                        id="password" 
                        value={password} 
                        onChange={(e) => setPassword(e.target.value)} 
                    />
                    {password && <div type="button" className="btn_clear" onClick={handleClearPassword}>
                                      <img className="btn_clear_img" alt="close_icon" src="img/clear.png" />
                                 </div>}
              </label>
              {loginError && <div className="error_message">{loginError}</div>}
              {loginError === '' && <div className="error_message"> </div>}
              <button className="btn_login" type="submit">로그인</button>
              <div className="txt_line"> 또는 </div>
              <div className="btn_col">
                    <button className="btn_naver">네이버 로그인</button>
                    <button className="btn_kakao">Kakao 로그인</button>
                    <button className="btn_Google">Google 로그인</button>
              </div>
              <NavLink to="/Join">
                    <div className="link_join">회원가입</div>
              </NavLink>             
        </form>
  </div>
);
}

export default LoginPage;