import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { customAxios } from "../../utils/axios";
import ModalComponent from "../modal/Modal";

const SignIn = () => {
   const [id, setId] = useState("");
   const [password, setPassword] = useState("");
   const [error, setError] = useState(false);
   const [message, setMessage] = useState("");
   const [show, setShow] = useState(false);

   const navigate = useNavigate();

   const handleSignIn = async (e: any) => {
      e.preventDefault();

      try {
         if (!id || !password) {
            setError(true)
            setMessage("빈칸이 존재합니다.");
            setShow(true);
            return;
         }

         await customAxios.post('/api/user/signin', {
            id,
            password
         });

         setMessage("로그인이 완료되었습니다.");
         setShow(true);

      } catch (error: any) {
         setError(true)
         setMessage(error.response.data);
         setShow(true);
         return;
      }
   }

   const closeModal = () => {
      setShow(false);
      if (!error) {
         navigate('/');
      }
   };

   return (
      <div onSubmit={handleSignIn}>
         <form action="submit">
            <input
               type="text"
               value={id}
               onChange={(e) => setId(e.target.value)}
               placeholder="아이디"
            />
            <input
               type="password"
               value={password}
               onChange={(e) => setPassword(e.target.value)}
               placeholder="비밀번호"
            />
            <button type="submit">로그인</button>
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

export default SignIn