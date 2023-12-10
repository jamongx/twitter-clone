
export const convertDateObjToDateStr = (dateObj) => {
    if (!dateObj || !dateObj.year || !dateObj.month || !dateObj.day) {
        return '0000-00-00';
    }
    const formattedYear = dateObj.year.toString().padStart(4, '0');
    const formattedMonth = dateObj.month.toString().padStart(2, '0');
    const formattedDay = dateObj.day.toString().padStart(2, '0');
    return `${formattedYear}-${formattedMonth}-${formattedDay}`;
}

export const convertDateStrToDateObj = (dateStr) => {
  if (!dateStr || typeof dateStr !== 'string' || !dateStr.match(/^\d{4}-\d{2}-\d{2}$/)) {
    return { year: '0000', month: '00', day: '00' };
  }
  const [year, month, day] = dateStr.split("-");
  return { 
    year:  parseInt(year,  10), 
    month: parseInt(month, 10), 
    day:   parseInt(day,   10)
  };
};