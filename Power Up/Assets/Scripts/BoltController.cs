using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TeamUtility.IO;

public class BoltController : MonoBehaviour {

    public PlayerID player;
    public int damage;
    public bool bloodthirsty;
    public Sprite vampireArrow;
    public Sprite doubleDamageArrow;
    public Sprite goldenGunArrow;

    public GameObject[] search;

    // Use this for initialization
    void Start () {

        if (damage == 1000) {
            GetComponent<SpriteRenderer>().sprite = goldenGunArrow;
        } else if (bloodthirsty == true) {
            GetComponent<SpriteRenderer>().sprite = vampireArrow;
        } else if (damage == 40) {
            GetComponent<SpriteRenderer>().sprite = goldenGunArrow;
        }

	}
	
	// Update is called once per frame
	void Update () {
		
	}

    void OnTriggerEnter2D(Collider2D other)
    {
		if ((other.gameObject.CompareTag ("Player") && !other.gameObject.GetComponent<CharacterControler>().getPlayer().Equals(player))) {
            if (bloodthirsty && other.gameObject.CompareTag("Player"))
            {
                search = GameObject.FindGameObjectsWithTag("Player");
                foreach (GameObject knight in search)
                {
                    if (knight.GetComponent<CharacterControler>().getPlayer() == player)
                    {
                        knight.GetComponent<CharacterControler>().heal();
                    }
                }
            }
		} else if (other.gameObject.CompareTag("wall") || other.gameObject.CompareTag("Sword")) {
            Destroy(this.gameObject);
        }
    }
}
