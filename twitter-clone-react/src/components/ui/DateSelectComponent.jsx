import React, { useEffect, useState } from "react";
import "./DateSelectComponent.css";
import {
  convertDateObjToDateStr,
  convertDateStrToDateObj,
} from "../../utils/formatters";

// the members of editedDate (year, month, day) are integer
// because the key of option is integer
const DateSelectComponent = ({ value, onChange }) => {
  const [editedDate, setEditedBirthDate] = useState(
    convertDateStrToDateObj(value)
  );

  useEffect(() => {
    onChange(convertDateObjToDateStr(editedDate));
  }, [editedDate]);

  const handleChange = (e) => {
    setEditedBirthDate((prevState) => ({
      ...prevState,
      [e.target.name]: e.target.value,
    }));
  };

  return (
    <div className="birth-date">
      <select name="month" value={editedDate.month} onChange={handleChange}>
        <option value="">Month</option>
        {Array.from({ length: 12 }, (_, i) => (
          <option key={i} value={i + 1}>
            {(i + 1).toString().padStart(2, "0")}
          </option>
        ))}
      </select>
      <select name="day" value={editedDate.day} onChange={handleChange}>
        <option value="">Day</option>
        {Array.from({ length: 31 }, (_, i) => (
          <option key={i} value={i + 1}>
            {(i + 1).toString().padStart(2, "0")}
          </option>
        ))}
      </select>
      <select name="year" value={editedDate.year} onChange={handleChange}>
        <option value="">Year</option>
        {Array.from({ length: 100 }, (_, i) => (
          <option key={i} value={new Date().getFullYear() - i}>
            {new Date().getFullYear() - i}
          </option>
        ))}
      </select>
    </div>
  );
};

export default DateSelectComponent;
