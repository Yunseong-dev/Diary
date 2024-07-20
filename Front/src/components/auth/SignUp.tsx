import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { customAxios } from "../../utils/axios";
import ModalComponent from "../modal/Modal";

const SignUp = () => {
   const [id, setId] = useState("");
   const [name, setName] = useState("");
   const [password, setPassword] = useState("");
   const [passwordCheck, setPasswordCheck] = useState("");
   const [error, setError] = useState(false);
   const [message, setMessage] = useState("");
   const [show, setShow] = useState(false);

   const navigate = useNavigate();

   const handleSignUp = async (e: any) => {
      e.preventDefault();

      try {
         if (!id || !password || !passwordCheck) {
            setError(true)
            setMessage("빈칸이 존재합니다.");
            setShow(true);
            return;
         }

         if (password !== passwordCheck) {
            setError(true)
            setMessage("비밀번호가 일치하지 않습니다.");
            setShow(true);
            return
         }

         await customAxios.post('/api/user/signup', {
            id,
            name,
            password
         });

         setMessage("회원가입이 완료되었습니다.");
         setShow(true);

      } catch (error: any) {
         if (error.response && error.response.data) {
            setError(true)
            setMessage(error.response.data);
            setShow(true);
            return;
         }
      }
   }

   const closeModal = () => {
      setShow(false);
      if (!error) {
         navigate('/');
      }
   };

   return (
      <div>
         <form onSubmit={handleSignUp}>
            <input
               type="text"
               value={id}
               onChange={(e) => setId(e.target.value)}
               placeholder="아이디"
            />
            <input
               type="text"
               value={name}
               onChange={(e) => setName(e.target.value)}
               placeholder="이름"
            />
            <input
               type="password"
               value={password}
               onChange={(e) => setPassword(e.target.value)}
               placeholder="비밀번호"
            />
            <input
               type="password"
               value={passwordCheck}
               onChange={(e) => setPasswordCheck(e.target.value)}
               placeholder="비밀번호 확인"
            />
            <button type="submit">가입하기</button>
         </form>

         <ModalComponent
            isOpen={show}
            error={error}
            onRequestClose={closeModal}
            message={message}
         />
      </div>
   )
}

export default SignUp;