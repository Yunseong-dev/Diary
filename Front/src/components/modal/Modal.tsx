import React from 'react';
import Modal from 'react-modal';

Modal.setAppElement('#root');

interface ModalComponentProps {
   isOpen: boolean;
   error: boolean;
   onRequestClose: () => void;
   message: React.ReactNode;
}

const ModalComponent: React.FC<ModalComponentProps> = ({ isOpen, error, onRequestClose, message }) => {
   const title = error ? "Error" : "Success";

   return (
      <Modal
         isOpen={isOpen}
         onRequestClose={onRequestClose}
         style={{
            content: {
               top: '50%',
               left: '50%',
               right: 'auto',
               bottom: 'auto',
               marginRight: '-50%',
               transform: 'translate(-50%, -50%)'
            }
         }}
      >
         <h2>{title}</h2>
         <div>{message}</div>
         <button onClick={onRequestClose}>Close</button>
      </Modal>
   );
}

export default ModalComponent;
