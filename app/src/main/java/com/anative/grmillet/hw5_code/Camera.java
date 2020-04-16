package com.anative.grmillet.hw5_code;

import android.opengl.Matrix;

/**
 * Created by grmillet on 2018-06-21.
 */

public class Camera {



    private float[] pos = new float[3]; // camera position
    private float[] uAxis = new float[3], vAxis = new float[3],
            nAxis = new float[3];
    private float fovy;
    private final float fovy_MAX = 80.0f, fovy_MIN = 1.0f;

    private final float DELTA_CONSTANT = 0.01f;
    private final float TO_RADIAN = 0.01745329f; // pi/180

    private float[] rotMat = new float[16];

    // //////////////////////////////////////////////////////////////////////
    public Camera() { // initial Value
        // 주어진 코드
        /*
        pos[0] = 0.0f;
        pos[1] = 2.0f;
        pos[2] = 10.0f;
        uAxis[0] = 1.0f;
        uAxis[1] = 0.0f;
        uAxis[2] = 0.0f;
        vAxis[0] = 0.0f;
        vAxis[1] = 1.0f;
        vAxis[2] = 0.0f;
        nAxis[0] = 0.0f;
        nAxis[1] = 0.0f;
        nAxis[2] = 1.0f;
        */

        // 내가 짠 코드: 옆면만 바라보기
        /*
        pos[0] = -345.0f;
        pos[1] = -240.0f;
        pos[2] = 25.0f;
        uAxis[0] = 0.571065f;
        uAxis[1] = -0.820905f;
        uAxis[2] = 0.0f;
        vAxis[0] = 0.0f;
        vAxis[1] = 0.0f;
        vAxis[2] = 1.0f;
        nAxis[0] = -0.820905f;
        nAxis[1] = -0.571064f;
        nAxis[2] = 0.0f;
        */

        // 내가 짠 코드: 비스듬이 바라보기
        pos[0] = -700.0f;
        pos[1] = 250.0f;
        pos[2] = 600.0f;
        uAxis[0] = -0.201820f;
        uAxis[1] = -0.979423f;
        uAxis[2] = 0.0f;
        vAxis[0] = 0.552192f;
        vAxis[1] = -0.113785f;
        vAxis[2] = 0.825916f;
        nAxis[0] = -0.808921f;
        nAxis[1] = 0.166687f;
        nAxis[2] = 0.563793f;

        fovy = 15.0f;
    }

    public void arrToMat(){
        // col major
        rotMat[0] = uAxis[0];
        rotMat[1] = uAxis[1];
        rotMat[2] = uAxis[2];
        rotMat[3] = 0.0f;

        rotMat[4] = vAxis[0];
        rotMat[5] = vAxis[1];
        rotMat[6] = vAxis[2];
        rotMat[7] = 0.0f;

        rotMat[8] = nAxis[0];
        rotMat[9] = nAxis[1];
        rotMat[10] = nAxis[2];
        rotMat[11] = 0.0f;

        rotMat[12] = 0.0f;
        rotMat[13] = 0.0f;
        rotMat[14] = 0.0f;
        rotMat[15] = 1.0f;
    }

    public void matToArr(){
        // col major
        uAxis[0] = rotMat[0];
        uAxis[1] = rotMat[1];
        uAxis[2] = rotMat[2];

        vAxis[0] = rotMat[4];
        vAxis[1] = rotMat[5];
        vAxis[2] = rotMat[6];

        nAxis[0] = rotMat[8];
        nAxis[1] = rotMat[9];
        nAxis[2] = rotMat[10];
    }

    // 주어진 코드
    public void Pitch(float dPitch) { // rotate u , v->n
        arrToMat();
        Matrix.rotateM(rotMat, 0, dPitch* DELTA_CONSTANT * 10.0f, 1.0f, 0.0f, 0.0f);
        matToArr();

        /*
        float rad_angle, sina, cosa;

        rad_angle = TO_RADIAN * dPitch * DELTA_CONSTANT * 10.0f;
        sina = (float)Math.sin(rad_angle);
        cosa = (float)Math.cos(rad_angle);
        // calculate v vector
        vAxis[0] = cosa * vAxis[0] + sina
                * (uAxis[1] * vAxis[2] - uAxis[2] * vAxis[1]);
        vAxis[1] = cosa * vAxis[1] + sina
                * (-uAxis[0] * vAxis[2] + uAxis[2] * vAxis[0]);
        vAxis[2] = cosa * vAxis[2] + sina
                * (uAxis[0] * vAxis[1] - uAxis[1] * vAxis[0]);

        nAxis[0] = uAxis[1] * vAxis[2] - uAxis[2] * vAxis[1];
        nAxis[1] = -uAxis[0] * vAxis[2] + uAxis[2] * vAxis[0];
        nAxis[2] = uAxis[0] * vAxis[1] - uAxis[1] * vAxis[0];

        nomalize(vAxis);
        nomalize(uAxis);
        nomalize(nAxis);
        */
    }

    // 내가 짠 코드
    public void Yaw(float dYaw) { // rotate v , n->u
        arrToMat();
        Matrix.rotateM(rotMat, 0, dYaw* DELTA_CONSTANT * 10.0f, 0.0f, 1.0f, 0.0f);
        matToArr();
    }

    public void Roll(float dRoll) { // rotate n , u->v
        arrToMat();
        Matrix.rotateM(rotMat, 0, dRoll* DELTA_CONSTANT * 10.0f, 0.0f, 0.0f, 1.0f);
        matToArr();
    }

    public void MoveForward(float delta) {
        pos[0] += delta * nAxis[0] * DELTA_CONSTANT;
        pos[1] += delta * nAxis[1] * DELTA_CONSTANT;
        pos[2] += delta * nAxis[2] * DELTA_CONSTANT;
    }

    // 주어진 코드
    public void MoveSideward(float delta) {
        pos[0] += delta * uAxis[0] * DELTA_CONSTANT;
        pos[1] += delta * uAxis[1] * DELTA_CONSTANT;
        pos[2] += delta * uAxis[2] * DELTA_CONSTANT;
    }

    // 내가 짠 코드
    public void MoveUpward(float delta) {
        pos[0] += delta * vAxis[0] * DELTA_CONSTANT;
        pos[1] += delta * vAxis[1] * DELTA_CONSTANT;
        pos[2] += delta * vAxis[2] * DELTA_CONSTANT;
    }

    public float[] GetPosition() {
        return pos;
    }

    public float[] GetViewMatrix() {
        float[] matrix = new float[16];
        matrix[0] = uAxis[0];
        matrix[4] = uAxis[1];
        matrix[8] = uAxis[2];
        matrix[12] = -(pos[0] * uAxis[0] + pos[1] * uAxis[1] + pos[2]
                * uAxis[2]);
        matrix[1] = vAxis[0];
        matrix[5] = vAxis[1];
        matrix[9] = vAxis[2];
        matrix[13] = -(pos[0] * vAxis[0] + pos[1] * vAxis[1] + pos[2]
                * vAxis[2]);
        matrix[2] = nAxis[0];
        matrix[6] = nAxis[1];
        matrix[10] = nAxis[2];
        matrix[14] = -(pos[0] * nAxis[0] + pos[1] * nAxis[1] + pos[2]
                * nAxis[2]);
        matrix[3] = 0.0f;
        matrix[7] = 0.0f;
        matrix[11] = 0.0f;
        matrix[15] = 1.0f;
        return matrix;
    }

    public float getFovy() {
        return fovy;
    }

    public void Zoom(float delta) {
        float newFovy = fovy + delta * DELTA_CONSTANT * 10.0f;
        if(fovy_MIN <= newFovy && newFovy <= fovy_MAX)
            fovy = newFovy;
    }

    public void nomalize(float[] vec) {
        float amp = (float)Math.sqrt(vec[0] * vec[0] + vec[1] * vec[1] + vec[2]
                * vec[2]);
        vec[0] /= amp;
        vec[1] /= amp;
        vec[2] /= amp;
    }

    public float[] getPos() {
        return pos;
    }


}

