import { ChartConfiguration } from 'chart.js';

export const graficaUsuarios: ChartConfiguration<'bar'> = {
  type: 'bar',
  data: {
    labels: ['Enero', 'Feb', 'Mar', 'Abr', 'May', 'Jun'],
    datasets: [
      {
        label: 'Usuarios activos',
        data: [12, 19, 8, 15, 22, 17],
        backgroundColor: '#1976d2',
      }
    ]
  },
  options: {
    responsive: true,
    plugins: {
      legend: { display: true },
      title: { display: true, text: 'Usuarios activos por mes' }
    }
  }
};
