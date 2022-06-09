using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class generatePickUps : MonoBehaviour {

	public float minX;
	public float minY;
	public float maxX;
	public float maxY;
	public float spwanTime;

	public List<GameObject> pickups;

    public GameObject smoke;

    public int countdown = 3;
    public float second = 1.0f;
    public bool start = false;

    private float timeLeft;
	// Use this for initialization
	void Start () {
		timeLeft = spwanTime;
	}
	
	// Update is called once per frame
	void Update () {

        second -= Time.deltaTime;
        if (second < 0 && start == false) {
            second = 1;
            countdown--;
            if (countdown == -1) {
                start = true;
            }
        }

        if (start) {

            timeLeft -= Time.deltaTime;
            if (timeLeft < 0) {
                timeLeft = spwanTime;
                int num = Random.Range(0, pickups.Count);
                float x = Random.Range(minX, maxX);
                float y = Random.Range(minY, maxY);
                Vector2 vector = new Vector2(x, y);

                GameObject o = pickups[num];
                Instantiate(o, vector, Quaternion.identity);
                Instantiate(smoke, vector, Quaternion.identity);
            }
        }
	}
}
