import { PerspectiveCamera } from "three";

export default class Camera {
  constructor(renderer) {
    const fov = 60;
    const near = 0.1;
    const far = 1000;
    const aspect = renderer.domElement.width / renderer.domElement.height;

    this.threeCamera = new PerspectiveCamera(fov, aspect, near, far);
    this.threeCamera.position.set(20, 15, 20);

    // Initial sizing
    this.updateSize(renderer);

    window.addEventListener("resize", () => this.updateSize(renderer), false);
  }

  updateSize(renderer) {
   
    this.threeCamera.aspect =
      renderer.domElement.width / renderer.domElement.height;
    this.threeCamera.updateProjectionMatrix();
  }
}
