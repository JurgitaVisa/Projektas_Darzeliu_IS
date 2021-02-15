import React from "react";
import "../../App.css";

const data = {
  kindergartenID: "",
  kindergartenName: "",
  kindergartenAddress: "",
  kindergartenRegion: "",
  kindergartenResource: {
    upTo3yrs: 0,
    upTo6yrs: 0,
  },
  savingStatus: false,
  savingError: false,
};

const handleSubmit = (event) => {
  event.preventDefault();
};

const validateText = (event) => {
  const target = event.target;
};

const handleChange = (event) => {
  validateText(event);
};

const resetForm = (event) => {};

function KindergartenInputForm() {
  return (
    <div>
      <form onSubmit={handleSubmit}>
        <h6 className="py-3"><b>Pridėti naują darželį </b></h6>
        <div className="form-group">
          <label htmlFor="kindergartenID">
            Įstaigos kodas <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control"
            name="kindergartenID"
            id="kindergartenID"
            value={data.kindergartenID}
            onChange={handleChange}
            onInvalid={validateText}
            required
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite įstaigos (darželio) kodą"
          />
        </div>
        
        <div className="form-group">
          <label htmlFor="kindergartenName">
            Pavadinimas <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control "
            name="kindergartenName"
            id="kindergartenName"
            value={data.kindergartenName}
            onChange={handleChange}
            onInvalid={validateText}
            required
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite darželio pavadinimą"
          />
        </div>

        <div className="form-group">
          <label htmlFor="kindergartenAddress">
            Adresas <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control"
            name="kindergartenAddress"
            id="kindergartenAddress"
            value={data.kindergartenAddress}
            onChange={handleChange}
            onInvalid={validateText}
            required
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite darželio adresą"
          />
        </div>

        <div className="form-group">
          <label htmlFor="kindergartenRegion">
            Seniūnija <span className="fieldRequired">*</span>
          </label>
          <select
            type="text"
            className="form-control"
            name="kindergartenRegion"
            id="kindergartenRegion"
            value={data.kindergartenRegion}
            onChange={handleChange}
            onInvalid={validateText}
            required
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite seniūniją, kuriai priskirtas darželis"
          />
        </div>
        <h6 className="py-3"><b>Vietų skaičius </b><span className="fieldRequired">*</span></h6>
        <div className="form-group">
          <label htmlFor="kindergartenAddress">
            2-3 metų grupėse
          </label>
          <input
            type="number"
            className="form-control"
            name="kindergartenAddress"
            id="kindergartenAddress"
            value={data.kindergartenAddress}
            onChange={handleChange}
            onInvalid={validateText}
            required
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite darželio adresą"
          />
        </div>

        <div className="form-group">
          <label htmlFor="kindergartenAddress">
            3-6 metų grupėse
          </label>
          <input
            type="number"
            className="form-control"
            name="kindergartenAddress"
            id="kindergartenAddress"
            value={data.kindergartenAddress}
            onChange={handleChange}
            onInvalid={validateText}
            required
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite darželio adresą"
          />
        </div>

        <button type="reset" className="btn btn-outline-danger mr-2 form-group">
          Išvalyti
        </button>
        <button
          type="submit"
          className="btn btn-primary form-group"
          id="btnSaveKindergarten"
          disabled={data.savingStatus}
        >
          {data.savingStatus ? "Pridedama..." : "Pridėti"}
        </button>
      </form>
    </div>
  );
}

export default KindergartenInputForm;
