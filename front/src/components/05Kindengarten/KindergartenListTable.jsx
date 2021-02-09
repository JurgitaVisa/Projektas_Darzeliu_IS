import React, { Component } from 'react';
import '../../App.css';

class KindergartenListTable extends Component {

    render() {
        const {
            darzeliai,
            inEditMode,
            editRowId,
            onEditData,
            onSave,
            onCancel,
            onChangeName,
            onChangeAddress,
            onChangeElderate,
            onChangeCapacity2to3,
            onChangeCapacity3to6,
            onDelete } = this.props;

        return (
            <div >

                <div className="table-responsive-md">

                    <table className="table">

                        <thead className="no-top-border">
                            <tr>
                                <th>Pavadinimas</th>
                                <th>Adresas</th>
                                <th>Seniūnija</th>
                                <th>Vietų 2-3 m. grupėje</th>
                                <th>Vietų 3-6 m. grupėje</th>
                                <th>Redaguoti duomenis</th>
                                <th>Ištrinti darželį</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                darzeliai.map((darzelis) => (
                                    <tr key={darzelis.id}>

                                        {inEditMode && editRowId === darzelis.id ?
                                            (
                                                <React.Fragment>
                                                    <td>
                                                        {
                                                            <input
                                                                type="text"
                                                                className="form-control"
                                                                id="txtKindergartenName"
                                                                name="name"
                                                                value={darzelis.name}
                                                                onChange={(event) => onChangeName(event.target.value)}
                                                                // onInvalid={(e) => validateText(e)}                                                   
                                                                required
                                                            />
                                                        }
                                                    </td>
                                                    <td>
                                                        {
                                                            <input
                                                                type="text"
                                                                className="form-control"
                                                                id="txtKindergartenAddress"
                                                                name="address"
                                                                value={darzelis.address}
                                                                onChange={(event) => onChangeAddress(event.target.value)}
                                                                // onInvalid={(e) => validateText(e)}                                                   
                                                                required
                                                            />
                                                        }
                                                    </td>
                                                    <td>
                                                        {
                                                            <input
                                                                type="text"
                                                                className="form-control"
                                                                id="txtKindergartenElderate"
                                                                name="elderate"
                                                                value={darzelis.elderate}
                                                                onChange={(event) => onChangeElderate(event.target.value)}
                                                                // onInvalid={(e) => validateText(e)}                                                   
                                                                required
                                                            />
                                                        }
                                                    </td>
                                                    <td>
                                                        {
                                                            <input
                                                                type="number"
                                                                min="0"
                                                                className="form-control"
                                                                id="nmbCapacity2to3"
                                                                name="capacity2to3"
                                                                value={darzelis.capacityAgeGroup2to3}
                                                                onChange={(event) => onChangeCapacity2to3(event.target.value)}
                                                                // onInvalid={(e) => validateText(e)}                                                   
                                                                required
                                                            />
                                                        }
                                                    </td>
                                                    <td>
                                                        {
                                                            <input
                                                                type="number"
                                                                min="0"
                                                                className="form-control"
                                                                id="nmbCapacity3to6"
                                                                name="capacity3to6"
                                                                value={darzelis.capacityAgeGroup3to6}
                                                                onChange={(event) => onChangeCapacity3to6(event.target.value)}
                                                                // onInvalid={(e) => validateText(e)}                                                   
                                                                required
                                                            />
                                                        }
                                                    </td>
                                                    <td>
                                                        {

                                                            <button
                                                                className="btn btn-outline-primary btn-sm mr-1"
                                                                id="btnSaveUpdatedKindergarten"
                                                                onClick={() => onSave({ id: darzelis.id, item: darzelis })}>
                                                                Saugoti
                                                            </button>
                                                            // <div className="row">
                                                            //     <div className="col-6">
                                                            //         <button
                                                            //             className="btn btn-outline-primary btn-sm mr-1"
                                                            //             id="btnSaveUpdatedKindergarten"
                                                            //             onClick={() => onSave({ id: darzelis.id, item: darzelis })}>
                                                            //             Saugoti
                                                            //     </button>
                                                            //     </div>
                                                            //     <div className="col-6">
                                                            //         <button
                                                            //             className="btn btn-outline-secondary btn-sm"
                                                            //             id="btnCancelUpdateKindergarten"
                                                            //             onClick={() => onCancel()}>
                                                            //             Atšaukti
                                                            //     </button>
                                                            //     </div>
                                                            // </div>
                                                        }
                                                    </td>
                                                </React.Fragment>
                                            ) : (
                                                <React.Fragment>
                                                    <td>{darzelis.name}</td>
                                                    <td>{darzelis.address}</td>
                                                    <td>{darzelis.elderate}</td>
                                                    <td>{darzelis.capacityAgeGroup2to3}</td>
                                                    <td>{darzelis.capacityAgeGroup3to6}</td>
                                                    <td> <button
                                                        className="btn btn-outline-primary btn-sm btn-block"
                                                        id="btnUpdateKindergarten"
                                                        onClick={() => onEditData(darzelis)}>
                                                        Redaguoti
                                                        </button>
                                                    </td>

                                                </React.Fragment>

                                            )}
                                        <td>
                                            <button
                                                onClick={() => onDelete(darzelis)}
                                                id="btnDeleteKindergarten"
                                                className="btn btn-outline-danger btn-sm btn-block">
                                                Ištrinti
                                            </button>
                                        </td>
                                    </tr>
                                ))
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}


export default KindergartenListTable;