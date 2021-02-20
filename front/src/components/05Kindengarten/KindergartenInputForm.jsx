import React, { useState, useEffect } from "react";
import "../../App.css";
import http from "../10Services/httpService";
import apiEndpoint from "../10Services/endpoint";
import swal from "sweetalert";

function KindergartenInputForm() {
  const initKindergartenData = {
    address: "",
    capacityAgeGroup2to3: 0,
    capacityAgeGroup3to6: 0,
    elderate: "",
    id: "",
    name: "",
  };

  var savingStatus = false;

  const [data, setData] = useState(initKindergartenData);
  const [elderates, setElderate] = useState([]);

  useEffect(() => {
    http
      .get(`${apiEndpoint}/api/darzeliai/manager/elderates`)
      .then((response) => {
        setElderate(response.data);
      })
      .catch((error) => {
        swal({
          text: "Įvyko klaida nuskaitant seniūnijas. " + error.response.data,
          button: "Gerai",
        });
      });
  }, [setElderate]);

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log("saugoma į serverį");
    console.log(data);
    savingStatus = true;
    http
      .post(`${apiEndpoint}/api/darzeliai/manager/createKindergarten`, data)
      .then((response) => {
        console.log("įrašyta: " + response.data);
        swal({
          text: "Naujas darželis „" + data.name + "“ pridėtas sėkmingai!",
          button: "Gerai",
        });
        savingStatus = false;
        resetForm(event);
      })
      .catch((error) => {
        if (error.response.status === 409) {
          swal({
            text:
              "Įvyko klaida įrašant naują darželį. " +
              error.response.data +
              "\n\nPatikrinkite duomenis ir bandykite dar kartą",
            button: "Gerai",
          });
        }
        savingStatus = false;
      });
  };

  const validateField = (event) => {
    const target = event.target;

    if (target.validity.valueMissing) {
      if (target.id === "elderate") {
        target.setCustomValidity("Reikia pasirinkti seniūniją");
      } else target.setCustomValidity("Būtina užpildyti šį laukelį");
    } else if (target.validity.patternMismatch) {
      if (target.id === "id")
        target.setCustomValidity("Įstaigos kodą turi sudaryti 9 skaitmenys");
      if (target.id === "name")
        target.setCustomValidity("Pavadinimas turi būti 3-50 simbolių ir negali prasidėti tarpu");
    } else {
      target.setCustomValidity("");
    }
  };

  const handleChange = (event) => {
    validateField(event);
    if (event.target.name === "capacityAgeGroup2to3") {
      setData({
        ...data,
        capacityAgeGroup2to3:
          event.target.value >= 0 ? Number(event.target.value) : 0,
      });
    } else if (event.target.name === "capacityAgeGroup3to6") {
      setData({
        ...data,
        savingError: false,
        capacityAgeGroup3to6:
          event.target.value >= 0 ? Number(event.target.value) : 0,
      });
    } else {
      setData({
        ...data,
        [event.target.name]: event.target.value,
      });
    }
  };

  const resetForm = (event) => {
    event.preventDefault();
    setData(initKindergartenData);
  };

  return (
    <div>
      <form onSubmit={handleSubmit} onReset={resetForm}>
        <h6 className="py-3">
          <b>Pridėti naują darželį </b>
        </h6>
        <div className="form-group">
          <label htmlFor="id">
            Įstaigos kodas <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control"
            name="id"
            id="id"
            value={data.id}
            onChange={handleChange}
            onInvalid={validateField}
            required
            pattern="\d{9}"
            placeholder="123456789"
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite įstaigos (darželio) kodą (9 skaitmenys)"
          />
        </div>

        <div className="form-group">
          <label htmlFor="name">
            Pavadinimas <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control "
            name="name"
            id="name"
            value={data.name}
            onChange={handleChange}
            onInvalid={validateField}
            required
            pattern="\S[\s\S]{2,49}"
            placeholder="3-50 simbolių"
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite darželio pavadinimą (nuo 3 iki 50 simbolių)"
          />
        </div>

        <div className="form-group">
          <label htmlFor="address">
            Adresas <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control"
            name="address"
            id="address"
            value={data.address}
            onChange={handleChange}
            onInvalid={validateField}
            required
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite darželio adresą"
          />
        </div>

        <div className="form-group">
          <label htmlFor="elderate">
            Seniūnija <span className="fieldRequired">*</span>
          </label>
          <select
            type="text"
            className="form-control"
            name="elderate"
            id="elderate"
            value={data.elderate}
            onChange={handleChange}
            onInvalid={validateField}
            required
            data-toggle="tooltip"
            data-placement="top"
            title="Pasirinkite seniūniją, kuriai priskiriamas darželis"
          >
            <option value="" disabled hidden label="Pasirinkite" />
            {elderates.map((option) => (
              <option value={option} label={option} key={option} />
            ))}
          </select>
        </div>
        <h6 className="py-3">
          <b>Vietų skaičius </b>
          <span className="fieldRequired">*</span>
        </h6>
        <div className="form-group">
          <label htmlFor="capacityAgeGroup2to3">2-3 metų grupėse</label>
          <input
            type="number"
            className="form-control"
            name="capacityAgeGroup2to3"
            id="capacityAgeGroup2to3"
            value={data.capacityAgeGroup2to3}
            onChange={handleChange}
            onInvalid={validateField}
            required
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite 2-3 metų amžiaus grupėse esančių vietų skaičių"
          />
        </div>

        <div className="form-group">
          <label htmlFor="capacityAgeGroup3to6">3-6 metų grupėse</label>
          <input
            type="number"
            className="form-control"
            name="capacityAgeGroup3to6"
            id="capacityAgeGroup3to6"
            value={data.capacityAgeGroup3to6}
            onChange={handleChange}
            onInvalid={validateField}
            required
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite 2-3 metų amžiaus grupėse esančių vietų skaičių"
          />
        </div>

        <button
          type="reset"
          className="btn btn-outline-danger mr-2 form-group"
          id="btnClearKindergartenForm"
        >
          Išvalyti
        </button>
        <button
          type="submit"
          className="btn btn-primary form-group"
          id="btnSaveKindergarten"
          disabled={savingStatus}
        >
          {savingStatus ? "Pridedama..." : "Pridėti"}
        </button>
      </form>
    </div>
  );
}

export default KindergartenInputForm;
