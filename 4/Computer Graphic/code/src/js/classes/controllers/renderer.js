import { WebGLRenderer, PCFShadowMap, GammaEncoding } from "three";

export default class Renderer {
  constructor(container) {
    this.container = container;


    this.threeRenderer = new WebGLRenderer({ antialias: true });

    this.threeRenderer.outputEncoding = GammaEncoding;
    this.threeRenderer.setPixelRatio(window.devicePixelRatio); 

    // Appends canvas
    container.appendChild(this.threeRenderer.domElement);
    this.threeRenderer.shadowMap.enabled = true;
    this.threeRenderer.shadowMap.type = PCFShadowMap;
  
    this.updateSize();

    document.addEventListener(
      "DOMContentLoaded",
      () => this.updateSize(),
      false
    );
    window.addEventListener("resize", () => this.updateSize(), false);
  }

  updateSize() {
    this.threeRenderer.setSize(window.innerWidth, window.innerHeight);
  }

  render(scene, camera) {
    this.threeRenderer.render(scene, camera);
  }
}
