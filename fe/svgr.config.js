export default {
  icon: true,
  expandProps: 'start',
  svgProps: {
    color: 'inherit', // 모든 SVG는 부모 color를 따르게
  },
  replaceAttrValues: {
    '#000': 'currentColor',
    '#000000': 'currentColor',
    black: 'currentColor',
    '#4E4B66': 'currentColor',
  },
  svgoConfig: {
    plugins: [
      {
        name: 'preset-default',
        params: {
          overrides: {
            removeAttrs: {
              attrs: '(fill|stroke)',
            },
          },
        },
      },
    ],
  },
};
