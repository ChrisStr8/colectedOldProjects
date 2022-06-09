using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using TeamUtility.IO;

public class GameManager : MonoBehaviour {

    public List<GameObject> players;
    public List<Vector3> spawnPoints;

    private List<GameObject> gamePlayers;

    private bool restarted = false;
    private static GameManager instanceRef;

    private int rounds = 0;

    private Dictionary<PlayerID, int> score;

    // Use this for initialization
    void Start() {
        gamePlayers = new List<GameObject>();
        spawnPlayers();
    }
    void Awake() {
        if (instanceRef == null) {
            instanceRef = this;
            DontDestroyOnLoad(gameObject);
        } else {
            DestroyImmediate(gameObject);
        }
        score = new Dictionary<PlayerID, int>();
    }

    void spawnPlayers() {
        for (int i = 0; i < players.Count; i++) {
            GameObject hud = null;
            GameObject player = Instantiate(players[i], spawnPoints[i], Quaternion.identity);
            switch (player.GetComponent<CharacterControler>().getPlayer()) {
                case PlayerID.One:
                    hud = GameObject.Find("Player1UI");
                    break;
                case PlayerID.Two:
                    hud = GameObject.Find("Player2UI");
                    break;
                case PlayerID.Three:
                    hud = GameObject.Find("Player3UI");
                    break;
                case PlayerID.Four:
                    hud = GameObject.Find("Player4UI");
                    break;
            }
            gamePlayers.Add(player);
            if (!score.ContainsKey(player.GetComponent<CharacterControler>().getPlayer()))
                score[player.GetComponent<CharacterControler>().getPlayer()] = 0;
            if (hud != null) {
                hud.GetComponentInChildren<HealthTrackerScript>().playerKnight = player;
                hud.GetComponentInChildren<CurrentPowerUpScript>().playerKnight = player;
            }
        }
    }

    // Update is called once per frame
    void Update() {
        for (int i = 0; i < 4; i++) {
            PlayerID player;
            switch (i) {
                case 0:
                    player = PlayerID.One;
                    break;
                case 1:
                    player = PlayerID.Two;
                    break;
                case 2:
                    player = PlayerID.Three;
                    break;
                case 3:
                    player = PlayerID.Four;
                    break;
                default:
                    player = PlayerID.One;
                    break;
            }
            GameObject hud = null;
            switch (player) {
                case PlayerID.One:
                    hud = GameObject.Find("Player1UI");
                    break;
                case PlayerID.Two:
                    hud = GameObject.Find("Player2UI");
                    break;
                case PlayerID.Three:
                    hud = GameObject.Find("Player3UI");
                    break;
                case PlayerID.Four:
                    hud = GameObject.Find("Player4UI");
                    break;
            }
            Debug.Log(score[player]);
            foreach (Image image in hud.GetComponentsInChildren<Image>()) {
                if (image.name == "Win1") {
                    if (score[player] >= 1) {
                        image.enabled = true;
                    } else {
                        image.enabled = false;
                    }
                } else if (image.name == "Win2") {
                    if (score[player] >= 2) {
                        image.enabled = true;
                    } else {
                        image.enabled = false;
                    }
                } else if (image.name == "Win3") {
                    if (score[player] >= 3) {
                        image.enabled = true;
                    } else {
                        image.enabled = false;
                    }
                } else if (image.name == "Win4") {
                    if (score[player] >= 4) {
                        image.enabled = true;
                    } else {
                        image.enabled = false;
                    }
                } else if (image.name == "Win5") {
                    if (score[player] >= 5) {
                        image.enabled = true;
                    } else {
                        image.enabled = false;
                    }
                }
            }
        }
        if (restarted) {
            spawnPlayers();
            restarted = false;
        }
        GameObject winner = null;

        foreach (GameObject player in gamePlayers) {
            if (player == null) {
                continue;
            }
            if (winner == null) {
                winner = player;
            } else {
                return;
            }
        }
        PlayerID id = winner.GetComponent<CharacterControler>().getPlayer();
        int nScore;
        if (score.ContainsKey(id)) {
            nScore = score[id] + 1;
        } else {
            nScore = 1;
        }
        score[id] = nScore;
        gamePlayers.Clear();
        restarted = true;
        rounds++;

        if (score.ContainsValue(5)) {
            DestroyImmediate(gameObject);
            SceneManager.LoadScene("Final Scenes/Main Menu");
        } else {
            SceneManager.LoadScene(SceneManager.GetActiveScene().name);
        }
        //Debug.Log(winner.GetComponent<CharacterControler>().getPlayer() + " Wins!");
    }
}
