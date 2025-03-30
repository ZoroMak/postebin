import React from "react";

import Modal from 'react-modal';


class ModalWindow extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            modalIsOpen: true
        };
    }

    closeModal = () => {
        this.setState({ modalIsOpen: false });
    };
    render() {
        return (
            <div>
                <Modal
                    isOpen={this.state.modalIsOpen}
                    onRequestClose={this.closeModal}
                    contentLabel="Example Modal"
                    style={{
                        overlay: {
                            backgroundColor: 'rgba(0, 0, 0, 0.75)',
                        },
                        content: {
                            top: '50%',
                            left: '50%',
                            right: 'auto',
                            bottom: 'auto',
                            marginRight: '-50%',
                            transform: 'translate(-50%, -50%)',
                            border: '1px solid #ccc',
                            overflow: 'auto',
                            WebkitOverflowScrolling: 'touch',
                            borderRadius: '4px',
                            outline: 'none',
                            padding: '20px',
                        },
                    }}
                >
                    <div style={{color: 'black'}}>
                        <p>{this.props.text}</p>
                        <button onClick={this.closeModal}>Закрыть</button>
                    </div>
                </Modal>
            </div>
        );
    }
}

export default ModalWindow;
