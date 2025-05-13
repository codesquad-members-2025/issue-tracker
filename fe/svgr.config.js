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
  },
  svgoConfig: {
    plugins: [
      {
        name: 'removeAttrs',
        params: {
          attrs: '(stroke|fill)', // 기존 fill/stroke 제거
        },
      },
    ],
  },
};
