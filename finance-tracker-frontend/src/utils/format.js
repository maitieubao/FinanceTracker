/**
 * Format number to currency string (e.g. 1,234.56)
 */
export const formatCurrency = (num) => {
  if (num === undefined || num === null || isNaN(num)) return '0.000';
  return Number(num).toLocaleString('en-US', {
    minimumFractionDigits: 0,
    maximumFractionDigits: 3,
  });
};
