import React, { Component } from 'react';
import swal from 'sweetalert';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import '../../App.css';

import KindergartenListTable from './KindergartenListTable';
import Pagination from './../08CommonComponents/Pagination';


export class KindergartenListContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            darzeliai: [],
            pageSize: 10,
            currentPage: 1,
            totalPages: 0,
            totalElements: 0,
            numberOfElements: 0,
            inEditMode: false,
            editRowId: "",
            editedKindergarten: null
        }
    }
    componentDidMount() {

        this.getKindergartenInfo(this.state.currentPage);

    }

    getKindergartenInfo(currentPage) {
        const { pageSize } = this.state;
        currentPage -= 1;
        http
            .get(`${apiEndpoint}/api/darzeliai/manager/page?page=${currentPage}&size=${pageSize}`)
            .then((response) => {

                this.setState({
                    darzeliai: response.data.content,
                    totalPages: response.data.totalPages,
                    totalElements: response.data.totalElements,
                    numberOfElements: response.data.numberOfElements,
                    currentPage: response.data.number + 1
                });

            }).catch(error => {
                console.log("Darzeliai container error", error.response);
                if (error && error.response.status === 401)
                
                swal({                   
                    text: "Puslapis pasiekiamas tik teises turintiems naudotojams",
                    button: "Gerai"
                });                  
                // this.props.history.replace("/home");
            }
            );
    }

    handleDelete = (item) => {
        const id = item.id;
        const { currentPage, numberOfElements } = this.state;
        const page = numberOfElements === 1 ? (currentPage - 1) : currentPage;
        console.log("Trinti darzeli", id);

        http
            .delete(`${apiEndpoint}/api/darzeliai/manager/delete/${id}`)
            .then((response) => {
                swal({                   
                    text: response.data,
                    button: "Gerai"
                });
                this.getKindergartenInfo(page);

            }).catch(error => {
                if (error && error.response.status > 400 && error.response.status < 500) 
                swal({                   
                    text: "Veiksmas neleidžiamas",
                    button: "Gerai"
                });              

            });
    }

    handleEditKindergarten = (item) => {
        console.log("Taisyti darzeli", item.id);

        this.setState({
            inEditMode: true,
            editRowId: item.id,
            editedKindergarten: item
        });
    }

    onCancel = () => {
        this.setState(
            {
                inEditMode: false,
                editRowId: "",
                editedKindergarten: null
            }
        )
    }

    handleChangeName = (newName) => {
        const kindergarten = this.state.editedKindergarten;
        kindergarten.name = newName;
        this.setState({ editedKindergarten: kindergarten });
    }

    handleChangeAddress = (newAddress) => {
        const kindergarten = this.state.editedKindergarten;
        kindergarten.address = newAddress;
        this.setState({ editedKindergarten: kindergarten });
    }

    handleChangeElderate = (newElderate) => {
        const kindergarten = this.state.editedKindergarten;
        kindergarten.elderate = newElderate;
        this.setState({ editedKindergarten: kindergarten });
    }

    handleChangeCapacity2to3 = (newCapacity2to3) => {
        const kindergarten = this.state.editedKindergarten;
        kindergarten.capacityAgeGroup2to3 = newCapacity2to3;
        this.setState({ editedKindergarten: kindergarten });
    }

    handleChangeCapacity3to6 = (newCapacity3to6) => {
        const kindergarten = this.state.editedKindergarten;
        kindergarten.capacityAgeGroup3to6 = newCapacity3to6;
        this.setState({ editedKindergarten: kindergarten });
    }

    handleSaveEdited = () => {
        const { editedKindergarten, editRowId } = this.state;

        console.log("Koreguoti axios.put", editRowId, editedKindergarten)

        http.put(`${apiEndpoint}/api/darzeliai/manager/update/${editRowId}`, editedKindergarten)
            .then(() => {
                this.onCancel();
                this.getKindergartenInfo(this.state.currentPage);
            }).catch(error => {
                console.log("KindergartenListContainer", error);
            })
    }


    handlePageChange = (page) => {
        this.setState({ currentPage: page });
        this.getKindergartenInfo(page);
    };

    render() {

        const { totalElements, pageSize, darzeliai, inEditMode, editRowId } = this.state;

        return (
            <React.Fragment>

                <KindergartenListTable
                    darzeliai={darzeliai}
                    inEditMode={inEditMode}
                    editRowId={editRowId}
                    onDelete={this.handleDelete}
                    onEditData={this.handleEditKindergarten}
                    onCancel={this.onCancel}
                    onChangeName={this.handleChangeName}
                    onChangeAddress={this.handleChangeAddress}
                    onChangeElderate={this.handleChangeElderate}
                    onChangeCapacity2to3={this.handleChangeCapacity2to3}
                    onChangeCapacity3to6={this.handleChangeCapacity3to6}
                    onSave={this.handleSaveEdited}
                />

                <Pagination
                    itemsCount={totalElements}
                    pageSize={pageSize}
                    onPageChange={this.handlePageChange}
                    currentPage={this.state.currentPage}
                />


            </React.Fragment>
        )
    }
}

export default KindergartenListContainer