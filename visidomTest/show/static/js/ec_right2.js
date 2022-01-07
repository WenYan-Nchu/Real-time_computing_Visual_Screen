var ec_right2 = echarts.init(document.getElementById('r2'),"dark");
var ec_right2_option = {
  title: {
    text: '支付方式',
    left: 'right'
  },
  tooltip: {
    trigger: 'item',
    formatter: '{b} : {c} ({d}%)'
  },
  series: [
    {
      name: 'type',
      type: 'pie',
      radius: [20, 140],
      center: ['50%', '50%'],
      roseType: 'radius',
      itemStyle: {
        borderRadius: 5
      },
      data: []
    }
  ]
};
ec_right2.setOption(ec_right2_option)